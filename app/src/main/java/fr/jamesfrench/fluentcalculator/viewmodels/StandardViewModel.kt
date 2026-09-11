package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ezylang.evalex.EvaluationException
import com.ezylang.evalex.Expression
import com.ezylang.evalex.config.ExpressionConfiguration
import com.ezylang.evalex.parser.ParseException
import fr.jamesfrench.fluentcalculator.R
import fr.jamesfrench.fluentcalculator.classes.Action
import fr.jamesfrench.fluentcalculator.classes.ButtonResponse
import fr.jamesfrench.fluentcalculator.classes.EvaluateResult
import fr.jamesfrench.fluentcalculator.classes.T
import fr.jamesfrench.fluentcalculator.utils.isValidOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class StandardViewModel : ViewModel() {
    private val configuration = ExpressionConfiguration.builder()
        .maxRecursionDepth(2000)
        .build()
    var equation = TextFieldState("")
    var showErrorEquation by mutableStateOf(false)
    var closedParentheses by mutableStateOf(false)
    var result: EvaluateResult by mutableStateOf(EvaluateResult.Success("", false))

    fun executeKeyboardAction(action: Action, value: String = ""): ButtonResponse {
        var success = ButtonResponse(false, 0)
        equation.edit {
            when (action) {
                Action.Append, Action.AddParentheses -> {
                    var value = value
                    if (action == Action.AddParentheses) {
                        value =
                            (if (closedParentheses) T.CloseParentheses.value else T.OpenParentheses.value).toString()
                    }

                    replace(selection.min, selection.max, value)
                    placeCursorBeforeCharAt(selection.max)
                    success = ButtonResponse(true, 0)
                    showErrorEquation = false
                }

                Action.Backspace -> {
                    if (selection.length > 0 || selection.min > 0) {
                        val offset =
                            if (selection.length > 0) 0 else 1 // Offset if no selection to remove previous character

                        delete(selection.min - offset, selection.max)
                        success = ButtonResponse(true, 0)
                        showErrorEquation = false
                    }
                }

                Action.ClearAll -> {
                    if (length > 0) {
                        delete(0, length)
                        success = ButtonResponse(true, 0)
                        showErrorEquation = false
                    }
                }

                Action.Equal -> {
                    if (result is EvaluateResult.Success && (result as EvaluateResult.Success).display) {
                        replace(0, length, (result as EvaluateResult.Success).resultString)
                        success = ButtonResponse(true, 1)
                        showErrorEquation = true
                    }
                }
            }
        }
        setClosedParentheses()
        return success
    }

    fun setClosedParentheses() {
        val before = equation.text.substring(0, equation.selection.max)
        val ratioParentheses =
            before.count { it == T.OpenParentheses.value } - before.count { it == T.CloseParentheses.value }
        closedParentheses =
            ratioParentheses > 0 && equation.text.getOrNull(equation.selection.min - 1) in T.Number.values + T.CloseParentheses.value
    }

    private data class Indexes(val start: Int, val end: Int)

    fun cleanExpression(text: String): String {
        val toRemove = mutableListOf<Indexes>()
        var text = text
        var i = 0

        while (true) {
            when (text.getOrNull(i)) {
                in T.Operator.values -> {
                    val operation = text.isValidOperator(i)

                    if (!operation.isReady) {
                        toRemove.add(0, Indexes(i, operation.endIndex))
                    }

                    i = operation.endIndex
                }
            }
            if (i >= text.lastIndex) {
                break
            } else {
                i += 1
            }
        }
        for (indexes in toRemove) {
            text = text.removeRange(indexes.start, indexes.end)
        }

        for (char in text.reversed()) {
            when (char) {
                '0' -> {
                    text = text.dropLast(1)
                }

                '.' -> {
                    text = text.dropLast(1); break
                }

                else -> {
                    break
                }
            }
        }

        repeat(
            maxOf(
                0,
                text.count { it == T.OpenParentheses.value } -
                        text.count { it == T.CloseParentheses.value }
            )
        ) {
            text += T.CloseParentheses.value
        }

        return text
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun evaluate(): EvaluateResult = withContext(Dispatchers.Default) {
        val cleanedExpression = cleanExpression(equation.text.toString())
        val expression = Expression(cleanedExpression, configuration)

        if (cleanedExpression.isEmpty()) {
            return@withContext EvaluateResult.Success("", false)
        }

        val executor = Executors.newSingleThreadExecutor()
        try {
            val future = executor.submit(Callable {
                expression.evaluate()
            })

            try {
                val result = future.get(200, TimeUnit.MILLISECONDS).numberValue
                val rawResult = result.toPlainString()
                val resultExpression = result
                    .setScale(result.scale().coerceAtMost(15), RoundingMode.HALF_UP)
                    .let {
                        if (it.abs() > BigDecimal(10_000_000_000)) {
                            it.toEngineeringString()
                        } else {
                            it.toPlainString()
                        }
                    }

                return@withContext EvaluateResult.Success(
                    resultExpression,
                    cleanedExpression != rawResult
                )
            } catch (exception: Exception) {
                val exceptionCauseMessage =
                    exception.cause?.message?.lowercase() // Yes, it's hardcoded, go cry about it, I cry about it too.
                val exceptionCause = exception.cause

                var messageID = 0
                var showImmediately = true
                val values: List<String> = listOf()

                when {
                    exception is TimeoutException -> {
                        future.cancel(true)
                        messageID = R.string.error_value_too_high_timeout
                        showImmediately = true
                    }

                    exceptionCause is EvaluationException && exceptionCauseMessage == "division by zero" -> {
                        messageID = R.string.error_division_by_zero
                        showImmediately = false
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "missing operand for operator" -> {
                        messageID = R.string.error_operand_missing
                        showImmediately = false
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "missing second operand for operator" -> {
                        messageID = R.string.error_second_operand_missing
                        showImmediately = false
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "unexpected token after infix operator" -> {
                        messageID = R.string.error_unexpected_token_after_infix_operator
                        showImmediately = true
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "unexpected closing brace" -> {
                        messageID = R.string.error_unexpected_closing_brace
                        showImmediately = true
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "number contains more than one decimal point" -> {
                        messageID = R.string.error_too_many_decimal
                        showImmediately = true
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "structure separator not allowed here" -> {
                        messageID = R.string.error_unexpected_decimal
                        showImmediately = true
                    }

                    exceptionCause is ParseException && exceptionCauseMessage == "too many operands" -> {
                        messageID = R.string.error_too_many_operands
                        showImmediately = true
                    }

                    exceptionCause is ArithmeticException && exceptionCauseMessage == "overflow" -> {
                        messageID = R.string.error_value_too_high_arithmetic
                        showImmediately = true
                    }
                }

                return@withContext EvaluateResult.Error(
                    showImmediately = showImmediately,
                    messageID = messageID,
                    message = exception.message,
                    values = values
                )

            }
        } finally {
            executor.shutdownNow()
        }
    }
}

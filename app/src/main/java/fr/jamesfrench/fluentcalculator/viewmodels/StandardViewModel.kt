package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ezylang.evalex.Expression
import fr.jamesfrench.fluentcalculator.classes.Action
import fr.jamesfrench.fluentcalculator.classes.ButtonResponse
import fr.jamesfrench.fluentcalculator.classes.EvaluateResult
import fr.jamesfrench.fluentcalculator.classes.T
import fr.jamesfrench.fluentcalculator.utils.isValidOperator

class StandardViewModel : ViewModel() {
    var equation = TextFieldState("")
    var showErrorEquation by mutableStateOf(false)

    fun executeKeyboardAction(action: Action, value: String = ""): ButtonResponse {
        var success = ButtonResponse(false, 0)
        equation.edit {
            when (action) {
                Action.Append -> {


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
                    }
                }

                Action.ClearAll -> {
                    if (length > 0) {
                        delete(0, length)
                        success = ButtonResponse(true, 0)
                    }
                }

                Action.Equal -> {
                    success = ButtonResponse(true, 1)
                    showErrorEquation = true
                    println("[$] ${"-".repeat(20)} SELECTION REPORT ${"-".repeat(20)}")
                    println(
                        "[$] ${
                            StringBuilder(this.toString())
                                .insert(selection.min, "[")
                                .insert(selection.max + 1, "]")
                        }"
                    )
                    println("[$] SELECT INDEX: ${selection.min} -> ${selection.max}")
                    println("[$] MAX INDEX: 0 -> ${maxOf(length - 1, 0)}")
                    println("[$] AT INDEX MIN: ${this.toString().getOrElse(selection.min) { '⚠' }}")
                    println("[$] AT INDEX MAX: ${this.toString().getOrElse(selection.max) { '⚠' }}")
                }

                else -> {}
            }
        }
        return success
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

    fun evaluate(): EvaluateResult {
        val cleanedExpression = cleanExpression(equation.text.toString())
        val expression = Expression(cleanedExpression)
        var result: String?

        if (cleanedExpression.isEmpty()) {
            return EvaluateResult("", null)
        }

        try {
            result = expression.evaluate().numberValue.toString()

            return EvaluateResult(result, null)
        } catch (e: Exception) {
            return EvaluateResult("", e)
        }
    }
}

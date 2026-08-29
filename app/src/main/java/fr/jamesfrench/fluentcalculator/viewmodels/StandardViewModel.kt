package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.lifecycle.ViewModel
import com.ezylang.evalex.Expression
import fr.jamesfrench.fluentcalculator.classes.Action
import fr.jamesfrench.fluentcalculator.classes.T
import fr.jamesfrench.fluentcalculator.utils.getType
import java.math.BigDecimal

class StandardViewModel : ViewModel() {
    var equation = TextFieldState("")

    fun executeKeyboardAction(action: Action, value: String = ""): Boolean {
        var success = false
        equation.edit {
            when (action) {
                Action.Append -> {
                    val isOperatorAndFirstCharacter =
                        value[0] in T.Operator.values &&
                                toString().getType(selection.min - 1) == T.Empty
                    val isAloneDot =
                        value[0] == '.' &&
                                toString().getType(selection.min - 1) in listOf(T.Empty, T.Operator)

                    when {
                        isOperatorAndFirstCharacter -> append('0')
                        isAloneDot -> append('0')
                    }


                    replace(selection.min, selection.max, value)
                    placeCursorBeforeCharAt(selection.max)
                    success = true
                }

                Action.Backspace -> {
                    if (selection.length > 0 || selection.min > 0) {
                        val offset =
                            if (selection.length > 0) 0 else 1 // Offset if no selection to remove previous character

                        delete(selection.min - offset, selection.max)
                        success = true
                    }
                }

                Action.ClearAll -> {
                    if (length > 0) {
                        delete(0, length)
                        success = true
                    }
                }

                Action.Equal -> {
                    println("[$] ${"-".repeat(20)} SELECTION REPORT ${"-".repeat(20)}")
                    println(
                        "[$] ${
                            StringBuilder(this.originalText)
                                .insert(selection.min, "[")
                                .insert(selection.max + 1, "]")
                        }"
                    )
                    println("[$] SELECT INDEX: ${selection.min} -> ${selection.max}")
                    println("[$] MAX INDEX: 0 -> ${maxOf(length - 1, 0)}")
                    println("[$] AT INDEX MIN: ${this.originalText.getOrElse(selection.min) { '⚠' }}")
                    println("[$] AT INDEX MAX: ${this.originalText.getOrElse(selection.max) { '⚠' }}")
                }

                else -> {}
            }
        }
        return success
    }

    private fun cleanExpression(equation: String): String {
        return equation
    }

    fun evaluate(): String {
        val cleanedExpression = cleanExpression(equation.text.toString())
        val expression = Expression(cleanedExpression)
        var result = BigDecimal(0)

        try {
            result = expression.evaluate().numberValue
        } catch (e: Exception) {
            println(e)
        }

        return result.toString()
    }
}

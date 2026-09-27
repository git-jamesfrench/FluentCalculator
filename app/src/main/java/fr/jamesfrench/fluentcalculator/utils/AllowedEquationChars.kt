package fr.jamesfrench.fluentcalculator.utils

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.delete

private val AllowedEquationChars = setOf(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    '+', '-', '*', '/', '^', '(', ')'
)

class CalculatorInputTransformation(decimal: String) : InputTransformation {
    private val decimalChars = decimal.toCharArray().toSet()

    override fun TextFieldBuffer.transformInput() {
        val current = toString()
        for (i in current.length - 1 downTo 0) {
            if (current[i] !in AllowedEquationChars && current[i] !in decimalChars) {
                delete(i, i + 1)
            }
        }
    }
}

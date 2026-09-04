package fr.jamesfrench.fluentcalculator.utils

import fr.jamesfrench.fluentcalculator.classes.Operation
import fr.jamesfrench.fluentcalculator.classes.T

// This is my masterpiece
fun String.isValidOperator(
    i: Int,
    isValid: Boolean = true,
    isReady: Boolean = true,
    firstIteration: Boolean = true
): Operation {
    val char = this.getOrElse(i) { T.Empty.value }
    // Check that the character before is a number
    val isReady =
        if (firstIteration) this.getOrElse(i - 1) { T.Empty.value } in T.Number.values + T.CloseParentheses.value else isReady

    return if (char in T.Number.values + T.OpenParentheses.value) { // A number, finish
        Operation(isValid, isReady, i)
    } else if (i > this.lastIndex || char == T.CloseParentheses.value) { // End reached, invalid & finish
        Operation(isValid, false, i)
    } else if (firstIteration && char in T.Operator.values) { // The first operator, continue
        this.isValidOperator(i + 1, isValid, isReady, firstIteration = false)
    } else if (char in T.Prefix.values && !firstIteration) { // A number prefix (- or +), continue
        this.isValidOperator(i + 1, isValid, isReady, firstIteration = false)
    } else { // Something else? Invalid but continue until number or end
        this.isValidOperator(i + 1, false, isReady, firstIteration = false)
    }
}

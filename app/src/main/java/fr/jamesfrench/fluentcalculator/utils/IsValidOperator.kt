package fr.jamesfrench.fluentcalculator.utils

import fr.jamesfrench.fluentcalculator.classes.Operation
import fr.jamesfrench.fluentcalculator.classes.T

// This is my masterpiece
fun String.isValidOperator(
    i: Int,
    isValid: Boolean = true,
    isReady: Boolean = true,
    iteration: Int = 0,
    isFirstPrefix: Boolean = false
): Operation {
    val char = this.getOrElse(i) { T.Empty.value }
    // Check that the character before is a number
    val isReady =
        if (iteration == 0) this.getOrElse(i - 1) { T.Empty.value } in T.Number.values + T.CloseParentheses.value else isReady

    return if (char in T.Number.values + T.OpenParentheses.value) { // A number, finish
        Operation(isValid, isReady || isFirstPrefix, i)
    } else if (i > this.lastIndex || char == T.CloseParentheses.value) { // End reached, invalid & finish
        Operation(isValid, false, i)
    } else if (iteration == 0 && char in T.Operator.values) { // The first operator, continue
        this.isValidOperator(i + 1, isValid, isReady, iteration = 0 + 1, char in T.Prefix.values)
    } else if (char in T.Prefix.values && iteration != 0) { // A number prefix (- or +), continue
        this.isValidOperator(i + 1, isValid, isReady, iteration = iteration + 1, isFirstPrefix)
    } else { // Something else? Invalid but continue until number or end
        this.isValidOperator(i + 1, false, isReady, iteration = iteration + 1, isFirstPrefix)
    }
}

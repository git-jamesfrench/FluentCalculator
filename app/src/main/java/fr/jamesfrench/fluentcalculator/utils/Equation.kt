package fr.jamesfrench.fluentcalculator.utils

import fr.jamesfrench.fluentcalculator.classes.T

fun String.getType(
    index: Int
): T {
    val atIndex = this.getOrNull(index)
    val afterIndex = this.getOrElse(index + 1) { T.Number.defaultValue }

    return when (atIndex) {
        null -> T.Empty
        T.Minus.defaultValue if afterIndex in T.Number.values -> T.Minus
        in T.Operator.values -> T.Operator
        else -> T.Number
    }
}
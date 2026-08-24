package fr.jamesfrench.fluentcalculator.utils

import fr.jamesfrench.fluentcalculator.classes.T

@Suppress("unused")
fun String.getType(
    index: Int,
    defaultType: Char
): T {
    val atIndex = this.getOrElse(index) { defaultType }
    val afterIndex = this.getOrElse(index + 1) { T.Number.defaultValue }

    return when (atIndex) {
        '-' if afterIndex in T.Number.values -> T.Minus
        in T.Operator.values -> T.Operator
        else -> T.Number
    }
}


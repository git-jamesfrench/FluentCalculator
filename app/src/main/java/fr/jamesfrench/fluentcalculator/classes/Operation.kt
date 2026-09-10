package fr.jamesfrench.fluentcalculator.classes

data class Operation(
    val isValid: Boolean, // Valid operator, e.g. 8*8 is valid, 9*/9 is invalid
    val isReady: Boolean, // Valid position, if the operator has a number before and after.
    val endIndex: Int
)

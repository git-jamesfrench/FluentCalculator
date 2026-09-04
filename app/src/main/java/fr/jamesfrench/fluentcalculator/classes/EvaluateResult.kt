package fr.jamesfrench.fluentcalculator.classes

data class EvaluateResult(
    val resultString: String,
    val error: Exception?
)
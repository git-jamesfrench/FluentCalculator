package fr.jamesfrench.fluentcalculator.classes

sealed interface EvaluateResult {
    data class Success(
        val resultString: String,
        val display: Boolean,
    ) : EvaluateResult

    data class Error(
        val showImmediately: Boolean,
        val messageID: Int = 0,
        val message: String? = null,
        val values: List<String> = listOf()
    ) : EvaluateResult
}
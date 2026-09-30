package fr.jamesfrench.fluentcalculator.classes

import org.matheclipse.core.expression.F
import org.matheclipse.core.interfaces.IExpr

sealed interface EvaluateResult {
    data class Success(
        val decimal: IExpr = F.Default,
        val rational: IExpr = F.Default,
        val display: Boolean,
    ) : EvaluateResult

    data class Error(
        val showImmediately: Boolean,
        val messageID: Int = 0,
        val message: String? = null,
        val values: List<String> = listOf()
    ) : EvaluateResult
}
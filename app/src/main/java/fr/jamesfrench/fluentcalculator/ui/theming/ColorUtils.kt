package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import kotlin.math.max
import kotlin.math.min

const val ContrastRatioAa = 4.5f

const val ContrastRatioAaLarge = 3.0f

const val ContrastRatioAaa = 7.0f

fun Color.lighten(fraction: Float): Color =
    lerp(this, Color.White, fraction.coerceIn(0f, 1f))

fun Color.darken(fraction: Float): Color =
    lerp(this, Color.Black, fraction.coerceIn(0f, 1f))

fun Color.blend(target: Color, fraction: Float): Color =
    lerp(this, target, fraction.coerceIn(0f, 1f))

val Color.isDarkColor: Boolean
    get() = luminance() < 0.5f

fun Color.contrastAgainst(background: Color): Float {
    val foregroundLuma = luminance()
    val backgroundLuma = background.luminance()
    val lighter = max(foregroundLuma, backgroundLuma)
    val darker = min(foregroundLuma, backgroundLuma)
    return (lighter + 0.05f) / (darker + 0.05f)
}

fun Color.ensureContrast(background: Color, minRatio: Float = ContrastRatioAa): Color {
    if (contrastAgainst(background) >= minRatio) return this

    val steps = 16
    var best: Color? = null
    var bestShift = Float.MAX_VALUE
    for (target in listOf(Color.White, Color.Black)) {
        for (step in 1..steps) {
            val shift = step.toFloat() / steps
            val candidate = lerp(this, target, shift)
            if (candidate.contrastAgainst(background) >= minRatio && shift < bestShift) {
                best = candidate
                bestShift = shift
            }
        }
    }
    return best
        ?: if (luminance() < background.luminance()) Color.White else Color.Black
}

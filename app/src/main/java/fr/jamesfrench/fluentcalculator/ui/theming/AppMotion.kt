package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.runtime.Immutable

@Immutable
data class AppMotion(
    val durations: Durations = Durations(),
    val easings: Easings = Easings(),
) {
    @Immutable
    data class Durations(
        val instant: Int = 100,
        val fast: Int = 150,
        val normal: Int = 250,
        val slow: Int = 400,
        val slower: Int = 600,
    )

    @Immutable
    data class Easings(
        val standard: Easing = FastOutSlowInEasing,
        val emphasized: Easing = CubicBezierEasing(0.83f, 0f, 0.17f, 1f),
        val decelerated: Easing = LinearOutSlowInEasing,
        val accelerated: Easing = FastOutLinearInEasing,
        val linear: Easing = LinearEasing,
    )
}

package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val background: Color,
    val onBackground: Color,
    val onBackgroundFaint1: Color,
    val onBackgroundFaint2: Color,
    val onBackgroundFaint3: Color,

    val surface: Color,
    val onSurface: Color,

    val neutral: Color,
    val activeNeutral: Color,
    val onNeutral: Color,

    val inverse: Color,
    val activeInverse: Color,
    val onInverse: Color,

    val accent: Color,
    val activeAccent: Color,
    val onAccent: Color,

    val error: Color,
    val onError: Color,

    val scrim: Color,

    val isDark: Boolean,

    val isStatusBarLight: Boolean,
) {
    val textSelectionColors: TextSelectionColors
        get() = TextSelectionColors(
            handleColor = accent,
            backgroundColor = accent.copy(alpha = SelectionBackgroundAlpha),
        )

    fun contentColorFor(backgroundColor: Color): Color? = when (backgroundColor) {
        background -> onBackground
        surface -> onSurface
        neutral -> onNeutral
        inverse -> onInverse
        accent -> onAccent
        error -> onError
        else -> null
    }

    companion object {
        const val SelectionBackgroundAlpha = 0.4f
    }
}

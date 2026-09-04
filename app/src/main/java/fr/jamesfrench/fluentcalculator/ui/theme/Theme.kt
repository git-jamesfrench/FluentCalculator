package fr.jamesfrench.fluentcalculator.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

data class FluentCalculatorColors(
    val background: Color,
    val onBackground: Color,
    val onBackgroundFaint: Color,
    val onBackgroundVeryFaint: Color,

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

    val isStatusBarLight: Boolean,
)

private val DarkColorScheme = FluentCalculatorColors(
    background = Black,
    onBackground = White,
    onBackgroundFaint = LighterWhite,
    onBackgroundVeryFaint = Gray,

    surface = DarkGray,
    onSurface = White,

    neutral = FaintDarkGray,
    activeNeutral = DarkGray,
    onNeutral = White,

    inverse = White,
    activeInverse = LighterWhite,
    onInverse = Black,

    accent = NothingRed,
    activeAccent = LighterNothingRed,
    onAccent = White,

    error = NothingRed,

    isStatusBarLight = true,
)

private val LightColorScheme = DarkColorScheme

val LocalAppColors = staticCompositionLocalOf<FluentCalculatorColors> {
    error("No color palette.")
}

@Composable
fun FluentCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !colorScheme.isStatusBarLight
        }
    }

    CompositionLocalProvider(LocalAppColors provides colorScheme) {
        content()
    }
}

object C {
    val colors: FluentCalculatorColors
        @Composable
        get() = LocalAppColors.current
}
package fr.jamesfrench.fluentcalculator.styling

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import fr.jamesfrench.fluentcalculator.data.stores.Theme

data class FluentCalculatorColors(
    val background: Color = Color.Black,
    val onBackground: Color = Color.White,
    val onBackgroundActive: Color = Color.White,
    val onBackgroundDisabled: Color = Color.White,
    val onBackgroundIgnored: Color = Color.White,

    val surface: Color = Color.White,
    val onSurface: Color = Color.Black,

    val neutral: Color = Color.White,
    val activeNeutral: Color = Color.White,
    val onNeutral: Color = Color.Black,

    val inverse: Color = Color.White,
    val activeInverse: Color = Color.White,
    val onInverse: Color = Color.Black,

    val accent: Color = Color.White,
    val activeAccent: Color = Color.White,
    val onAccent: Color = Color.Black,

    val error: Color = Color.White,

    val isStatusBarLight: Boolean = false,
)

val LocalAppColors = staticCompositionLocalOf<FluentCalculatorColors> {
    error("No color palette.")
}

@Composable
fun FluentCalculatorTheme(
    theme: Theme?,
    content: @Composable () -> Unit
) {
    val theme = remember(theme) {
        if (theme != null) {
            FluentCalculatorColors(
                background = Color(theme.background.target.value),
                onBackground = Color(theme.onBackground.target.value),
                onBackgroundActive = Color(theme.onBackgroundActive.target.value),
                onBackgroundDisabled = Color(theme.onBackgroundDisabled.target.value),
                onBackgroundIgnored = Color(theme.onBackgroundIgnored.target.value),

                surface = Color(theme.surface.target.value),
                onSurface = Color(theme.onSurface.target.value),

                neutral = Color(theme.neutral.target.value),
                activeNeutral = Color(theme.activeNeutral.target.value),
                onNeutral = Color(theme.onNeutral.target.value),

                inverse = Color(theme.inverse.target.value),
                activeInverse = Color(theme.activeInverse.target.value),
                onInverse = Color(theme.onInverse.target.value),

                accent = Color(theme.accent.target.value),
                activeAccent = Color(theme.activeAccent.target.value),
                onAccent = Color(theme.onAccent.target.value),

                error = Color(theme.error.target.value),

                isStatusBarLight = theme.isStatusBarLight
            )
        } else {
            FluentCalculatorColors()
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                theme.isStatusBarLight
        }
    }

    CompositionLocalProvider(LocalAppColors provides theme) {
        content()
    }
}

object S {
    val colors: FluentCalculatorColors
        @Composable
        get() = LocalAppColors.current
}
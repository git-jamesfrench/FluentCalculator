package fr.jamesfrench.fluentcalculator.ui.theming

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("AppColors is not provided: wrap this composable in FluentTheme { … }.")
}

private val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("AppTypography is not provided: wrap this composable in FluentTheme { … }.")
}

private val LocalAppShapes = staticCompositionLocalOf<AppShapes> {
    error("AppShapes is not provided: wrap this composable in FluentTheme { … }.")
}

private val LocalAppSpacing = staticCompositionLocalOf<AppSpacing> {
    error("AppSpacing is not provided: wrap this composable in FluentTheme { … }.")
}

private val LocalAppMotion = staticCompositionLocalOf<AppMotion> {
    error("AppMotion is not provided: wrap this composable in FluentTheme { … }.")
}

object FluentTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val spacing: AppSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacing.current

    val motion: AppMotion
        @Composable
        @ReadOnlyComposable
        get() = LocalAppMotion.current

    val isDark: Boolean
        @Composable
        @ReadOnlyComposable
        get() = colors.isDark
}

@Composable
fun FluentTheme(
    theme: Theme,
    adjustSystemBars: Boolean = true,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    if (adjustSystemBars && !view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !theme.colors.isStatusBarLight
        }
    }
    CompositionLocalProvider(
        LocalAppColors provides theme.colors,
        LocalAppTypography provides theme.typography,
        LocalAppShapes provides theme.shapes,
        LocalAppSpacing provides theme.spacing,
        LocalAppMotion provides theme.motion,
        LocalTextSelectionColors provides theme.colors.textSelectionColors,
    ) {
        content()
    }
}

@Composable
fun FluentTheme(
    family: ThemeFamily = Themes.Default,
    mode: ThemeMode = ThemeMode.SYSTEM,
    adjustSystemBars: Boolean = true,
    content: @Composable () -> Unit,
) {
    FluentTheme(
        theme = family.resolve(mode.resolve(isSystemInDarkTheme())),
        adjustSystemBars = adjustSystemBars,
        content = content,
    )
}

@Composable
fun FluentTheme(
    state: ThemeState,
    adjustSystemBars: Boolean = true,
    content: @Composable () -> Unit,
) {
    FluentTheme(
        family = state.family,
        mode = state.mode,
        adjustSystemBars = adjustSystemBars,
        content = content,
    )
}

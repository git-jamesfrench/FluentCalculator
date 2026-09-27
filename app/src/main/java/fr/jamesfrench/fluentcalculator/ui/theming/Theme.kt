package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.runtime.Immutable

enum class ThemeMode {
    SYSTEM,

    LIGHT,

    DARK;

    fun resolve(systemInDarkTheme: Boolean): Boolean = when (this) {
        SYSTEM -> systemInDarkTheme
        LIGHT -> false
        DARK -> true
    }
}

@Immutable
data class Theme(
    val id: String,
    val colors: AppColors,
    val typography: AppTypography,
    val shapes: AppShapes,
    val spacing: AppSpacing,
    val motion: AppMotion,
) {
    val isDark: Boolean
        get() = colors.isDark
}

@Immutable
data class ThemeFamily(
    val id: String,
    val label: String,
    val light: Theme,
    val dark: Theme,
) {
    init {
        require(id.isNotBlank()) { "ThemeFamily id must not be blank." }
        require(!light.isDark) { "ThemeFamily '$id': light variant must not be dark." }
        require(dark.isDark) { "ThemeFamily '$id': dark variant must be dark." }
    }

    fun resolve(isDark: Boolean): Theme = if (isDark) dark else light

    fun withTypography(typography: AppTypography): ThemeFamily = copy(
        light = light.copy(typography = typography),
        dark = dark.copy(typography = typography),
    )
}

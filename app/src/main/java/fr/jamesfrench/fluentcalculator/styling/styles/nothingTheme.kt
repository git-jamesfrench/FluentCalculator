package fr.jamesfrench.fluentcalculator.styling.styles

import fr.jamesfrench.fluentcalculator.data.stores.Color
import fr.jamesfrench.fluentcalculator.data.stores.Theme

fun nothingTheme(): Theme {
    // Declaration
    val theme = Theme(
        name = "Nothing Theme",
        isSystem = true,

        isStatusBarLight = false
    )
    // Colors
    val nothingRed = Color(name = "Nothing Red", value = 0xFFC8102E)
    val lighterNothingRed = Color(name = "Lighter Nothing Red", value = 0xFF860C20)

    val white = Color(name = "White", value = 0xFFFFFFFF)
    val lighterWhite = Color(name = "Lighter White", value = 0xFFDEDEDE)
    val lightGray = Color(name = "Light Gray", value = 0xFF9C9C9C)
    val gray = Color(name = "Gray", value = 0xFF6E6E6E)

    val black = Color(name = "Black", value = 0xFF000000)
    val faintDarkGray = Color(name = "Faint Dark Gray", value = 0xFF19191a)
    val darkGray = Color(name = "Dark Gray", value = 0xFF323234)
    // Values
    theme.background.target = black
    theme.onBackground.target = white
    theme.onBackgroundActive.target = lighterWhite
    theme.onBackgroundDisabled.target = lightGray
    theme.onBackgroundIgnored.target = gray

    theme.surface.target = darkGray
    theme.onSurface.target = white

    theme.neutral.target = faintDarkGray
    theme.activeNeutral.target = darkGray
    theme.onNeutral.target = white

    theme.inverse.target = white
    theme.activeInverse.target = lightGray
    theme.onInverse.target = black

    theme.accent.target = nothingRed
    theme.activeAccent.target = lighterNothingRed
    theme.onAccent.target = white

    theme.error.target = nothingRed

    return theme
}
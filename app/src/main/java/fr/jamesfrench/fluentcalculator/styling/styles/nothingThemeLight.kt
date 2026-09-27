package fr.jamesfrench.fluentcalculator.styling.styles

import fr.jamesfrench.fluentcalculator.data.stores.Color
import fr.jamesfrench.fluentcalculator.data.stores.Theme

fun nothingThemeLight(): Theme {
    // Declaration
    val theme = Theme(
        name = "Nothing Theme Light",
        isSystem = true,

        isStatusBarLight = true
    )
    // Colors
    val nothingRed = Color(name = "Nothing Red", value = 0xFFC8102E)
    val lighterNothingRed = Color(name = "Lighter Nothing Red", value = 0xFF860C20)

    val black = Color(name = "Black", value = 0xFF000000)
    val lighterBlack = Color(name = "Lighter Black", value = 0xFF232323)
    val lightBlack = Color(name = "Light Black", value = 0xFF585858)
    val gray = Color(name = "Gray", value = 0xFF767676)

    val white = Color(name = "White", value = 0xFFFFFFFF)
    val faintGray = Color(name = "Faint Gray", value = 0xFFEBEBED)
    val darkerFaintGray = Color(name = "Darker Faint Gray", value = 0xFFC7C7C9)
    // Values
    theme.background.target = white
    theme.onBackground.target = black
    theme.onBackgroundActive.target = lighterBlack
    theme.onBackgroundDisabled.target = lightBlack
    theme.onBackgroundIgnored.target = gray

    theme.surface.target = faintGray
    theme.onSurface.target = white

    theme.neutral.target = faintGray
    theme.activeNeutral.target = darkerFaintGray
    theme.onNeutral.target = black

    theme.inverse.target = black
    theme.activeInverse.target = lightBlack
    theme.onInverse.target = white

    theme.accent.target = nothingRed
    theme.activeAccent.target = lighterNothingRed
    theme.onAccent.target = white

    theme.error.target = nothingRed

    return theme
}
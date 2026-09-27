package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp

@Immutable
data class AppTypography(
    val equation: TextStyle,
    val resultProminent: TextStyle,
    val result: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
)

fun defaultTypography(
    inter: FontFamily = FontFamily.Default,
    nDot: FontFamily = FontFamily.Default,
): AppTypography = AppTypography(
    equation = TextStyle(
        fontFamily = inter,
        fontSize = 30.sp,
        fontFeatureSettings = "\"case\" 1",
    ),
    resultProminent = TextStyle(
        fontFamily = nDot,
        fontSize = 55.sp,
        baselineShift = BaselineShift(-0.2f),
    ),
    result = TextStyle(
        fontFamily = nDot,
        fontSize = 30.sp,
        baselineShift = BaselineShift(-0.2f),
    ),
    body = TextStyle(
        fontFamily = inter,
        fontSize = 25.sp,
    ),
    caption = TextStyle(
        fontFamily = inter,
        fontSize = 14.sp,
    ),
)

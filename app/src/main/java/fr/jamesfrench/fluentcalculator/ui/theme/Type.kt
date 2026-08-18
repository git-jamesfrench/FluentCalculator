package fr.jamesfrench.fluentcalculator.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import fr.jamesfrench.fluentcalculator.R

val interFont = FontFamily(
    Font(R.font.inter)
)

val ndotFont = FontFamily(
    Font(R.font.ndot77jpextended)
)

val mediumInter = TextStyle(
    fontFamily = interFont,
    fontSize = 25.sp,
)

val largeInter = TextStyle(
    fontFamily = interFont,
    fontSize = 30.sp,
)

val largeNDot = TextStyle(
    fontFamily = ndotFont,
    fontSize = 30.sp,
    baselineShift = BaselineShift(-0.2f)
)
package fr.jamesfrench.fluentcalculator.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import fr.jamesfrench.fluentcalculator.ui.theme.C
import androidx.compose.material3.Text as MText

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 25.sp,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = C.colors.text,
) {
    MText(
        text = text,
        fontSize = fontSize,
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}
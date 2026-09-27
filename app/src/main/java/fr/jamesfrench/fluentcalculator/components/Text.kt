package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.mediumInter

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = mediumInter,
    color: Color = C.colors.onBackground,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(color = color)
    )
}

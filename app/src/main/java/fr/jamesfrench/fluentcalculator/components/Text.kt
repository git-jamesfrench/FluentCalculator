package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import fr.jamesfrench.fluentcalculator.styling.S
import fr.jamesfrench.fluentcalculator.styling.mediumInter

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = mediumInter,
    color: Color = S.colors.onBackground,
) {
    BasicText(
        text = text,
        style = style.copy(color = color),
        modifier = modifier
    )
}
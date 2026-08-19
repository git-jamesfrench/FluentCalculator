package fr.jamesfrench.fluentcalculator.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.mediumInter
import androidx.compose.material3.Text as MText

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = mediumInter,
    color: Color = C.colors.onBackground,
) {
    MText(
        text = text,
        style = style,
        color = color,
        modifier = modifier
    )
}
package fr.jamesfrench.fluentcalculator.components

import androidx.compose.material3.Text as MText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fr.jamesfrench.fluentcalculator.ui.theme.C

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = C.colors.text,
) {
    MText(
        text = text,
        color = color,
        modifier = modifier
    )
}
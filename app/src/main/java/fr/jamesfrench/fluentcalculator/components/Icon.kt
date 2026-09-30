package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.styling.S

@Composable
fun Icon(
    icon: ImageVector,
    iconDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = S.colors.onBackground,
) {
    Image(
        imageVector = icon,
        contentDescription = iconDescription,
        modifier = modifier.size(size),
        colorFilter = ColorFilter.tint(color)
    )
}
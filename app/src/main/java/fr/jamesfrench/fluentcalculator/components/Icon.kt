package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.ui.theme.C
import androidx.compose.material3.Icon as MIcon

@Composable
fun Icon(
    icon: ImageVector,
    iconDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = C.colors.onBackground,
) {
    MIcon(
        imageVector = icon,
        tint = color,
        contentDescription = iconDescription,
        modifier = modifier.size(size)
    )
}
package fr.jamesfrench.fluentcalculator.components

import androidx.compose.material3.Icon as MIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import fr.jamesfrench.fluentcalculator.ui.theme.C

@Composable
fun Icon(
    icon: ImageVector,
    iconDescription: String,
    modifier: Modifier = Modifier,
    color: Color = C.colors.text,
) {
    MIcon(
        imageVector = icon,
        tint = color,
        contentDescription = iconDescription,
        modifier = modifier
    )
}
package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.largeInter
import fr.jamesfrench.fluentcalculator.ui.theme.largeNDot

enum class BigButtonVariant {
    Gray,
    Accent,
    Inverse
}

@Composable
fun BigButton(
    text: String,
    variant: BigButtonVariant,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                when (variant) {
                    BigButtonVariant.Gray -> C.colors.faintHighlight
                    BigButtonVariant.Accent -> C.colors.accent
                    BigButtonVariant.Inverse -> C.colors.inverse
                },
                RoundedCornerShape(100)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = when (variant) {
                BigButtonVariant.Gray -> largeInter
                BigButtonVariant.Accent -> largeNDot
                BigButtonVariant.Inverse -> largeNDot
            },
            color = when (variant) {
                BigButtonVariant.Gray -> C.colors.text
                BigButtonVariant.Accent -> C.colors.onAccent
                BigButtonVariant.Inverse -> C.colors.onInverse
            }
        )
    }
}
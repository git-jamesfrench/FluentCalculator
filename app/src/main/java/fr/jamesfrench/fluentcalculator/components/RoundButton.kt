package fr.jamesfrench.fluentcalculator.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.ui.theme.C

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val haptic = LocalHapticFeedback.current

    val color = animateColorAsState(
        targetValue = if (isPressed) C.colors.faintHighlight else C.colors.background,
        animationSpec = if (isPressed)
            tween(0, easing = EaseOut)
        else
            tween(100, easing = CubicBezierEasing(0.55f, 0.055f, 0.675f, 0.19f))
    )


    Box(
        modifier = modifier
            .background(color.value, RoundedCornerShape(100))
            .clickable(
                indication = null,
                interactionSource = interactionSource,
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                onClick()
            }
            .padding(16.dp)

    ) {
        content()
    }
}
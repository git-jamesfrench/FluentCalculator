package fr.jamesfrench.fluentcalculator.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.classes.ButtonResponse
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.largeInter
import fr.jamesfrench.fluentcalculator.ui.theme.largeNDot
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

enum class BigButtonVariant {
    Gray,
    Accent,
    Inverse
}

@Composable
fun BigButton(
    text: String,
    variant: BigButtonVariant,
    modifier: Modifier = Modifier,
    onClick: () -> ButtonResponse,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    val color by animateColorAsState(
        if (isPressed) when (variant) {
            BigButtonVariant.Gray -> C.colors.activeNeutral
            BigButtonVariant.Accent -> C.colors.activeAccent
            BigButtonVariant.Inverse -> C.colors.activeInverse
        } else when (variant) {
            BigButtonVariant.Gray -> C.colors.neutral
            BigButtonVariant.Accent -> C.colors.accent
            BigButtonVariant.Inverse -> C.colors.inverse
        },
        if (isPressed)
            tween(0, easing = EaseIn)
        else
            tween(250, easing = EaseOut)
    )
    val borderWidth by animateFloatAsState(
        if (isPressed) 4f else 0f
    )

    Box(
        modifier = modifier
            .drawBehind {
                val strokeWidth = borderWidth.dp.toPx()
                val halfStroke = strokeWidth / 2
                drawRoundRect(
                    color = color,
                    topLeft = Offset(-halfStroke, -halfStroke),
                    size = Size(size.width + strokeWidth, size.height + strokeWidth),
                    cornerRadius = CornerRadius(2000f)
                )
            }
            .clickable(
                indication = null,
                interactionSource = interactionSource,
            ) {
                val click = onClick()
                if (click.vibrate) {
                    when (click.vibrationType) {
                        0 -> {
                            haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                        }

                        1 -> {
                            scope.launch {
                                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                                delay(100.milliseconds)
                                haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                            }
                        }

                        else -> {}
                    }
                }
            },
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
                BigButtonVariant.Gray -> C.colors.onNeutral
                BigButtonVariant.Accent -> C.colors.onAccent
                BigButtonVariant.Inverse -> C.colors.onInverse
            }
        )
    }
}
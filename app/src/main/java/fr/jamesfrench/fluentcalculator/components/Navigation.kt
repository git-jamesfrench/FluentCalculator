package fr.jamesfrench.fluentcalculator.components

import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jamesfrench.fluentcalculator.R
import fr.jamesfrench.fluentcalculator.icons.LucideCog
import fr.jamesfrench.fluentcalculator.icons.LucideHistory
import fr.jamesfrench.fluentcalculator.ui.theme.C

private fun interpolate(values: List<Int>, index: Float): Float {
    val lowerIndex = index.toInt().coerceIn(0, values.size - 1)
    val upperIndex = (lowerIndex + 1).coerceIn(0, values.size - 1)
    val fraction = index - lowerIndex

    val lowerValue = values[lowerIndex]
    val upperValue = values[upperIndex]

    return lowerValue + (upperValue - lowerValue) * fraction
}

@Composable
fun Navigation(
    options: List<String>,
    modifier: Modifier = Modifier
) {
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    var selectorSize by remember { mutableStateOf(IntSize.Zero) }
    var selected by remember { mutableFloatStateOf(0f) }
    val lengths = mutableListOf(0).also { list ->
        options.forEach { string ->
            val width = textMeasurer.measure(string, TextStyle(fontSize = 25.sp)).size.width

            list.add(width)
        }
    }
    val lengthsSummed = mutableListOf<Int>().also { list ->
        lengths.forEachIndexed { i, number ->
            list.add(number + list.getOrElse(i - 1) { 0 })
        }
    }
    val selectedAnimated by animateFloatAsState(
        selected,
        tween(150, easing = EaseInOutQuint)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(1.dp, C.colors.surface, RoundedCornerShape(100))
    ) {
        // Button 1
        RoundButton(onClick = { selected -= 1 }) {
            Icon(LucideCog, stringResource(R.string.settings_description), size = 24.dp)
        }
        // Tabs
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clipToBounds()
                .onGloballyPositioned { coordinates ->
                    selectorSize = coordinates.size
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset -> println("DRAG START: $offset") },
                        onDragEnd = { println("DRAG END") },
                        onDragCancel = { println("DRAG CANCEL") },
                        onHorizontalDrag = { change, dragAmount -> println("HORIZONTAL DRAG: $change, $dragAmount") }
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            options.forEachIndexed { index, string ->
                Text(
                    string,
                    modifier = Modifier.offset {
                        IntOffset(
                            lengthsSummed[index] // Size of element
                                    + selectorSize.width / 2 - interpolate(
                                lengths,
                                selectedAnimated + 1
                            ).toInt() / 2 // Centering
                                    - interpolate(
                                lengthsSummed,
                                selectedAnimated
                            ).toInt() // Selection
                                    + index * 100 - (selectedAnimated * 100).toInt(), // Centering
                            0
                        )
                    }
                )
            }
            Box( // Black gradient
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0f to C.colors.background,
                                0.15f to Color.Transparent,
                                0.85f to Color.Transparent,
                                1f to C.colors.background,
                            )
                        )
                    )
            )
        }
        // Button 2
        RoundButton(onClick = { selected += 1 }) {
            Icon(LucideHistory, stringResource(R.string.history_icon), size = 24.dp)
        }
    }
}
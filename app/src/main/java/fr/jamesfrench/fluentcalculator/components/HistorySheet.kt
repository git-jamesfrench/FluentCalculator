package fr.jamesfrench.fluentcalculator.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.R
import fr.jamesfrench.fluentcalculator.classes.HistoryEntry
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.largeInter
import fr.jamesfrench.fluentcalculator.ui.theme.mediumInter

private const val SheetAnimationDuration = 250

@Composable
fun HistorySheet(
    history: List<HistoryEntry>,
    onDismiss: () -> Unit,
    onSelect: (HistoryEntry) -> Unit,
    onDelete: (HistoryEntry) -> Unit,
    onClearAll: () -> Unit,
) {
    var shown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { shown = true }

    BackHandler { onDismiss() }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = shown,
            enter = fadeIn(tween(SheetAnimationDuration)),
            exit = fadeOut(tween(SheetAnimationDuration))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismiss
                    )
            )
        }

        AnimatedVisibility(
            visible = shown,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                tween(SheetAnimationDuration, easing = EaseInOutQuint)
            ) { it } + fadeIn(tween(SheetAnimationDuration)),
            exit = slideOutVertically(
                tween(SheetAnimationDuration, easing = EaseInOutQuint)
            ) { it } + fadeOut(tween(SheetAnimationDuration))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        C.colors.background,
                        RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(
                            C.colors.onBackgroundFaint3,
                            RoundedCornerShape(100)
                        )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.history),
                        style = largeInter,
                        color = C.colors.onBackground
                    )
                    if (history.isNotEmpty()) {
                        Text(
                            stringResource(R.string.clear_history),
                            style = mediumInter,
                            color = C.colors.accent,
                            modifier = Modifier
                                .clickable { onClearAll() }
                                .padding(8.dp)
                        )
                    }
                }

                if (history.isEmpty()) {
                    Text(
                        stringResource(R.string.no_history),
                        color = C.colors.onBackgroundFaint2,
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 420.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(history, key = { it.id }) { entry ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(C.colors.neutral, RoundedCornerShape(16.dp))
                                    .clickable { onSelect(entry) }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        entry.equation,
                                        color = C.colors.onBackgroundFaint2,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Text(
                                        "= " + entry.result,
                                        style = largeInter,
                                        color = C.colors.onBackground,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                Text(
                                    "✕",
                                    color = C.colors.onBackgroundFaint2,
                                    modifier = Modifier
                                        .clickable { onDelete(entry) }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

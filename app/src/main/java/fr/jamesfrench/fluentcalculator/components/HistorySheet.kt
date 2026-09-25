package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.R
import fr.jamesfrench.fluentcalculator.classes.HistoryEntry
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.largeInter
import fr.jamesfrench.fluentcalculator.ui.theme.mediumInter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorySheet(
    history: List<HistoryEntry>,
    onDismiss: () -> Unit,
    onSelect: (HistoryEntry) -> Unit,
    onDelete: (HistoryEntry) -> Unit,
    onClearAll: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = C.colors.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

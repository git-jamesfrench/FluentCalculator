package fr.jamesfrench.fluentcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.jamesfrench.fluentcalculator.components.HistorySheet
import fr.jamesfrench.fluentcalculator.pages.CalculatorStandard
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.FluentCalculatorTheme
import fr.jamesfrench.fluentcalculator.viewmodels.StandardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FluentCalculatorTheme {
                val vm: StandardViewModel = viewModel()
                var showHistory by remember { mutableStateOf(false) }

                val layoutDirection = LocalLayoutDirection.current
                val insets = WindowInsets.safeDrawing
                val screenPadding = with(LocalDensity.current) {
                    PaddingValues(
                        maxOf(12.dp, insets.getLeft(this, layoutDirection).toDp()),
                        maxOf(12.dp, insets.getTop(this).toDp()),
                        maxOf(12.dp, insets.getRight(this, layoutDirection).toDp()),
                        maxOf(12.dp, insets.getBottom(this).toDp()),
                    )
                }

                Box( // Background
                    modifier = Modifier
                        .fillMaxSize()
                        .background(C.colors.background)
                ) {
                    CalculatorStandard(
                        screenPadding = screenPadding,
                        vm = vm,
                        onHistoryClick = { showHistory = true }
                    )

                    if (showHistory) {
                        HistorySheet(
                            history = vm.history,
                            onDismiss = { showHistory = false },
                            onSelect = { entry ->
                                vm.loadHistoryEntry(entry)
                                showHistory = false
                            },
                            onDelete = { entry -> vm.deleteHistoryEntry(entry.id) },
                            onClearAll = { vm.clearHistory() }
                        )
                    }
                }
            }
        }
    }
}

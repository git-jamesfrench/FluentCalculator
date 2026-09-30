package fr.jamesfrench.fluentcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.jamesfrench.fluentcalculator.pages.CalculatorStandard
import fr.jamesfrench.fluentcalculator.styling.FluentCalculatorTheme
import fr.jamesfrench.fluentcalculator.styling.S
import fr.jamesfrench.fluentcalculator.viewmodels.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        super.window.isNavigationBarContrastEnforced = false

        setContent {
            Content()
        }
    }
}

@Composable
fun Content(
    d: DataViewModel = viewModel()
) {
    FluentCalculatorTheme(d.currentTheme) {
        Box(
            modifier = Modifier
                .background(S.colors.background)
                .fillMaxSize()
        ) {
            val layoutDirection = LocalLayoutDirection.current
            val windowInsets = WindowInsets.safeDrawing.asPaddingValues()
            val screenPadding = PaddingValues(
                maxOf(0.dp, 12.dp - windowInsets.calculateLeftPadding(layoutDirection)),
                maxOf(0.dp, 12.dp - windowInsets.calculateTopPadding()),
                maxOf(0.dp, 12.dp - windowInsets.calculateRightPadding(layoutDirection)),
                maxOf(0.dp, 12.dp - windowInsets.calculateBottomPadding()),
            )

            Box( // Background
                modifier = Modifier
                    .fillMaxSize()
                    .background(S.colors.background)
                    .padding(windowInsets)
            ) {
                CalculatorStandard(
                    screenPadding,
                    viewModel()
                )
            }
        }
    }
}
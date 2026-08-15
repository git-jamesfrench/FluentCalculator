package fr.jamesfrench.fluentcalculator.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.components.Navigation
import fr.jamesfrench.fluentcalculator.components.Text

@Composable
fun CalculatorStandard(
    innerPadding: PaddingValues
) {
    val layoutDirection = LocalLayoutDirection.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                maxOf(0.dp, 12.dp - innerPadding.calculateLeftPadding(layoutDirection)),
                maxOf(0.dp, 12.dp - innerPadding.calculateTopPadding()),
                maxOf(0.dp, 12.dp - innerPadding.calculateRightPadding(layoutDirection)),
                maxOf(0.dp, 12.dp - innerPadding.calculateBottomPadding()),
            )
            .background(Color.Blue)
    ) {
        Navigation()
        Text("Hello, World!")
    }
}
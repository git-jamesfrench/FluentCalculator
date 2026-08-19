package fr.jamesfrench.fluentcalculator.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.classes.Action
import fr.jamesfrench.fluentcalculator.classes.ButtonData
import fr.jamesfrench.fluentcalculator.components.BigButton
import fr.jamesfrench.fluentcalculator.components.BigButtonVariant
import fr.jamesfrench.fluentcalculator.components.Navigation

private val Buttons = listOf(
    listOf(
        ButtonData("AC", BigButtonVariant.Inverse, Action.ClearAll),
        ButtonData("%", BigButtonVariant.Accent, Action.Append, "%"),
        ButtonData("(", BigButtonVariant.Accent, Action.AddParentheses),
        ButtonData("÷", BigButtonVariant.Accent, Action.Append, "/"),
    ),
    listOf(
        ButtonData("7", BigButtonVariant.Gray, Action.Append, "7"),
        ButtonData("8", BigButtonVariant.Gray, Action.Append, "8"),
        ButtonData("9", BigButtonVariant.Gray, Action.Append, "9"),
        ButtonData("×", BigButtonVariant.Accent, Action.Append, "*"),
    ),
    listOf(
        ButtonData("4", BigButtonVariant.Gray, Action.Append, "4"),
        ButtonData("5", BigButtonVariant.Gray, Action.Append, "5"),
        ButtonData("6", BigButtonVariant.Gray, Action.Append, "6"),
        ButtonData("–", BigButtonVariant.Accent, Action.Append, "-"),
    ),
    listOf(
        ButtonData("1", BigButtonVariant.Gray, Action.Append, "1"),
        ButtonData("2", BigButtonVariant.Gray, Action.Append, "2"),
        ButtonData("3", BigButtonVariant.Gray, Action.Append, "3"),
        ButtonData("+", BigButtonVariant.Accent, Action.Append, "+"),
    ),
    listOf(
        ButtonData("←", BigButtonVariant.Inverse, Action.Backspace),
        ButtonData("0", BigButtonVariant.Gray, Action.Append, "0"),
        ButtonData(",", BigButtonVariant.Gray, Action.Append, ","),
        ButtonData("=", BigButtonVariant.Accent, Action.Equal),
    ),
)

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
    ) {
        Navigation(
            listOf(
                "Conversion",
                "Standard",
                "Scientific",
            ),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {}
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Buttons.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item.forEach { item ->
                        BigButton(
                            item.text,
                            item.variant,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            {}
                        )
                    }
                }
            }
        }
    }
}
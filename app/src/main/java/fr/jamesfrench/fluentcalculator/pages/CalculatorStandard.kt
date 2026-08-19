package fr.jamesfrench.fluentcalculator.pages

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.classes.Action
import fr.jamesfrench.fluentcalculator.classes.ButtonData
import fr.jamesfrench.fluentcalculator.components.BigButton
import fr.jamesfrench.fluentcalculator.components.BigButtonVariant
import fr.jamesfrench.fluentcalculator.components.Navigation
import fr.jamesfrench.fluentcalculator.viewmodels.StandardViewModel

private val ButtonsVertical = listOf(
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
private val ButtonsHorizontal = listOf(
    listOf(
        ButtonData("AC", BigButtonVariant.Inverse, Action.ClearAll),
        ButtonData("÷", BigButtonVariant.Accent, Action.Append, "/"),
        ButtonData("–", BigButtonVariant.Accent, Action.Append, "-"),
        ButtonData("(", BigButtonVariant.Accent, Action.AddParentheses),
    ),
    listOf(
        ButtonData("%", BigButtonVariant.Accent, Action.Append, "%"),
        ButtonData("×", BigButtonVariant.Accent, Action.Append, "*"),
        ButtonData("+", BigButtonVariant.Accent, Action.Append, "+"),
        ButtonData("=", BigButtonVariant.Accent, Action.Equal),
    ),
    listOf(
        ButtonData("7", BigButtonVariant.Gray, Action.Append, "7"),
        ButtonData("4", BigButtonVariant.Gray, Action.Append, "4"),
        ButtonData("1", BigButtonVariant.Gray, Action.Append, "1"),
        ButtonData("←", BigButtonVariant.Inverse, Action.Backspace),
    ),
    listOf(
        ButtonData("8", BigButtonVariant.Gray, Action.Append, "8"),
        ButtonData("5", BigButtonVariant.Gray, Action.Append, "5"),
        ButtonData("2", BigButtonVariant.Gray, Action.Append, "2"),
        ButtonData("0", BigButtonVariant.Gray, Action.Append, "0"),
    ),
    listOf(
        ButtonData("9", BigButtonVariant.Gray, Action.Append, "9"),
        ButtonData("6", BigButtonVariant.Gray, Action.Append, "6"),
        ButtonData("3", BigButtonVariant.Gray, Action.Append, "3"),
        ButtonData(",", BigButtonVariant.Gray, Action.Append, ","),
    )
)

@Composable
fun CalculatorStandard(
    innerPadding: PaddingValues,
    vm: StandardViewModel
) {
    val layoutDirection = LocalLayoutDirection.current
    val orientation = LocalConfiguration.current.orientation
    val layout = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ButtonsHorizontal
        else -> ButtonsVertical
    }

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
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Result(
                    modifier = Modifier.weight(1f),
                    vm
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    layout.forEach { item ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            item.forEach { item ->
                                BigButton(
                                    item.text,
                                    item.variant,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    {
                                        vm.executeKeyboardAction(item.action, item.value)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Result(
                    modifier = Modifier.weight(1f),
                    vm
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    layout.forEach { item ->
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
                                    {
                                        vm.executeKeyboardAction(item.action, item.value)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun Result(
    modifier: Modifier = Modifier,
    vm: StandardViewModel
) {
    Column(
        modifier = modifier
    ) {
        Navigation(
            listOf(
                "Conversion",
                "Standard",
                "Scientific",
            ),
        )
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {

        }
    }

}
package fr.jamesfrench.fluentcalculator.pages

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jamesfrench.fluentcalculator.classes.Action
import fr.jamesfrench.fluentcalculator.classes.ButtonData
import fr.jamesfrench.fluentcalculator.classes.T
import fr.jamesfrench.fluentcalculator.components.BigButton
import fr.jamesfrench.fluentcalculator.components.BigButtonVariant
import fr.jamesfrench.fluentcalculator.components.Navigation
import fr.jamesfrench.fluentcalculator.components.Text
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.largeInter
import fr.jamesfrench.fluentcalculator.ui.theme.veryLargeNDot
import fr.jamesfrench.fluentcalculator.utils.DisableSoftKeyboard
import fr.jamesfrench.fluentcalculator.utils.copy
import fr.jamesfrench.fluentcalculator.utils.isValidOperator
import fr.jamesfrench.fluentcalculator.viewmodels.StandardViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

private val ButtonsVertical = listOf(
    listOf(
        ButtonData("AC", BigButtonVariant.Inverse, Action.ClearAll),
        ButtonData("(", BigButtonVariant.Accent, Action.Append, "("),
        ButtonData(")", BigButtonVariant.Accent, Action.Append, ")"),
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
        ButtonData(",", BigButtonVariant.Gray, Action.Append, "."),
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
    screenPadding: PaddingValues,
    vm: StandardViewModel
) {
    val orientation = LocalConfiguration.current.orientation
    val layout = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> ButtonsHorizontal
        else -> ButtonsVertical
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Result(
                    screenPadding.copy(end = 0.dp, bottom = 0.dp),
                    modifier = Modifier.weight(1f),
                    vm
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(screenPadding.copy(start = 0.dp))
                ) {
                    layout.forEach { item ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            item.forEach { item ->
                                BigButton(
                                    if (item.action == Action.AddParentheses) "(" else item.text,
                                    item.variant,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    {
                                        return@BigButton vm.executeKeyboardAction(
                                            item.action,
                                            item.value
                                        )
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
                    screenPadding.copy(bottom = 0.dp),
                    modifier = Modifier.weight(1f),
                    vm
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(screenPadding.copy(top = 0.dp))
                ) {
                    layout.forEach { item ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            item.forEach { item ->
                                BigButton(
                                    if (item.action == Action.AddParentheses) "(" else item.text,
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

class EquationTransformation(
    private val disabledColor: Color = Color.Black,
    private val errorColor: Color = Color.Black,
    private val autoAddedColor: Color = Color.Black
) : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        // Validation
        val text = toString()
        var i = 0

        for (i in text.indices) {
            if (text[i] in T.Operator.values) {
                when (text[i]) {
                    '-' -> replace(i, i + 1, "−")
                    '*' -> replace(i, i + 1, "×")
                    '/' -> replace(i, i + 1, "÷")
                }
            }
        }

        while (true) {
            when (text.getOrNull(i)) {
                in T.Operator.values -> {
                    val operation = text.isValidOperator(i)

                    if (!operation.isReady) {
                        addStyle(SpanStyle(disabledColor), i, operation.endIndex)
                    } else if (!operation.isValid) {
                        addStyle(SpanStyle(errorColor), i, operation.endIndex)
                    }

                    i = operation.endIndex
                }
            }
            if (i >= text.lastIndex) {
                break
            } else {
                i += 1
            }
        }
        repeat( // Close unclosed parentheses
            maxOf(
                0,
                text.count { it == T.OpenParentheses.value } -
                        text.count { it == T.CloseParentheses.value }
            )
        ) {
            insert(length, T.CloseParentheses.value.toString())
            addStyle(SpanStyle(autoAddedColor), length - 1, length)
        }
    }
}

@Composable
private fun Result(
    screenPadding: PaddingValues,
    modifier: Modifier = Modifier,
    vm: StandardViewModel
) {
    val focusRequester = remember { FocusRequester() }
    val selectionColors = TextSelectionColors(
        handleColor = C.colors.accent,
        backgroundColor = C.colors.accent.copy(alpha = 0.4f)
    )
    val spacing = screenPadding.plus(PaddingValues(start = 12.dp, end = 12.dp))
        .copy(top = 0.dp, bottom = 0.dp)

    val equationScroll = rememberScrollState()
    val resultScroll = rememberScrollState()

    var result by remember { mutableStateOf(vm.evaluate()) }
    println("[$] COMPOSITION")

    LaunchedEffect(vm.equation) {
        snapshotFlow { vm.equation.text.toString() }
            .distinctUntilChanged()
            .collect {
                result = vm.evaluate()
                equationScroll.scrollTo(equationScroll.maxValue)
            }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Navigation(
            listOf(
                "Conversion",
                "Standard",
                "Scientific",
            ),
            Modifier.padding(screenPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {


            CompositionLocalProvider(
                LocalTextSelectionColors provides selectionColors
            ) {
                DisableSoftKeyboard {
                    BasicTextField(
                        state = vm.equation,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .focusRequester(focusRequester),
                        textStyle = largeInter.copy(
                            color = C.colors.onBackground,
                            textAlign = TextAlign.Right
                        ),
                        cursorBrush = SolidColor(C.colors.accent),
                        outputTransformation = EquationTransformation(
                            C.colors.onBackgroundVeryFaint,
                            C.colors.error,
                            C.colors.onBackgroundFaint
                        ),
                        scrollState = equationScroll,
                        decorator = { inner -> // Screen padding is calculated here, only to optimize clickable space.
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(spacing),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                inner()
                            }
                        }
                    )
                }
            }
//            Text(
//                text = vm.cleanExpression(vm.equation.text.toString()),
//                style = veryLargeNDot.copy(
//                    color = C.colors.onBackground,
//                    textAlign = TextAlign.Right
//                ),
//                modifier = Modifier
//                    .verticalScroll(resultScroll)
//                    .fillMaxWidth()
//                    .weight(1f)
//                    .padding(spacing)
//            )
            Text(
                text = if (result.error != null && vm.showErrorEquation) "⚠ ${result.error?.message}" else result.resultString,
                style = veryLargeNDot.copy(
                    color = C.colors.onBackground,
                    textAlign = TextAlign.Right,
                    fontSize = (if (result.error == null) 55.sp else 30.sp)
                ),
                modifier = Modifier
                    .verticalScroll(resultScroll)
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(spacing + PaddingValues(top = (if (result.error != null) 15.dp else 0.dp)))
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import fr.jamesfrench.fluentcalculator.classes.Action

//fun String.replaceChars(map: Map<Char, Char>): String {
//    return this.map { char -> map[char] ?: char }.joinToString("")
//}

private fun TextFieldValue.get(
    index: Int,
    defaultValue: Char
): Char {
    return this.text.getOrElse(index) { defaultValue }
}

class StandardViewModel : ViewModel() {
    companion object {
//        private val replacementsEquation = mapOf(
//            '-' to '–',
//            '*' to '×',
//            '/' to '÷',
//            '.' to ','
//        )

        //        private val charsAllowingClose =
//            setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '%', ')')
        private val operators = setOf('+', '*', '/', '-')

        //        private val notAfterEquation = setOf("+", "*", "/", "%")
        private val Number = '0'
        private val Operator = '+'
    }

    var equation by mutableStateOf(TextFieldValue(""))
        private set

//    fun getParenthesesState(): String {
//        val lastChar = equation.text.[equation.selection]
//
//        return when (lastChar) {
//            null -> "("
//            in charsAllowingClose if equation.count { it == '(' } > equation.count { it == ')' } -> ")"
//            else -> "("
//        }
//    }

//    fun cleanEquation(equation: String): String {
//        return equation.replaceChars(replacementsEquation)
//    }

    fun insertTextInTextField(textFieldValue: TextFieldValue, insert: String) {
        val text = textFieldValue.text
        val selection = textFieldValue.selection

        val newText = text.replaceRange(selection.min, selection.max, insert)
        val newSelection = selection.min + insert.length

        equation = TextFieldValue(
            newText,
            TextRange(newSelection)
        )
    }

    fun selectionAction(newValue: TextFieldValue) {
        equation = equation.copy(
            selection = newValue.selection
        )
    }


    fun executeKeyboardAction(action: Action, value: String? = null): Boolean {
        val selection = equation.selection

        when (action) {
            Action.Append -> {
                if (value.isNullOrEmpty()) return false
                when {
                    value[0] in operators -> {
                        when {
                            (
                                    value[0] == '-' &&
                                            equation.get(selection.min - 2, Number) !in operators &&
                                            equation.get(
                                                selection.min - 1,
                                                Operator
                                            ) in operators &&
                                            equation.get(selection.max, Number) !in operators
                                    ) -> {
                                insertTextInTextField(equation, value)
                                return true
                            }

                            (
                                    equation.get(selection.min - 1, Number) in operators ||
                                            equation.get(selection.max, Number) in operators
                                    ) -> {
                                return false
                            }

                            else -> {
                                insertTextInTextField(equation, value)
                                return true
                            }
                        }
                    }

                    else -> {
                        insertTextInTextField(equation, value)
                        return true
                    }
                }
            }

            Action.ClearAll -> {
                equation = equation.copy(text = "", selection = TextRange(0))
                return true
            }

            Action.Backspace -> {
                equation = equation.copy(equation.text.dropLast(1))
            }

            Action.AddParentheses -> {
            }

            Action.Equal -> {
            }
        }
        return false
    }
}

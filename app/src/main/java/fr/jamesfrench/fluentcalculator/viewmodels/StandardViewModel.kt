package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import fr.jamesfrench.fluentcalculator.classes.Action

class StandardViewModel : ViewModel() {
    var equation by mutableStateOf(TextFieldValue(""))
        private set

    fun selectionAction(newValue: TextFieldValue) {
        equation = equation.copy(
            selection = newValue.selection
        )
    }


    fun executeKeyboardAction(action: Action, value: String? = null): Boolean {
        val text = StringBuilder(equation.text)
        var selection = equation.selection
        var success = false

        when (action) {
            Action.Append -> {
                text.append(value)
                selection = TextRange(selection.min + (value?.length ?: 0))
                success = true
            }

            Action.Backspace -> {

            }
            Action.Equal -> {
                println("[$] ${"-".repeat(20)} SELECTION REPORT ${"-".repeat(20)}")
                println(
                    "[$] ${
                        StringBuilder(text)
                            .insert(selection.min, "[")
                            .insert(selection.max + 1, "]")
                    }"
                )
                println("[$] SELECT INDEX: ${selection.min} -> ${selection.max}")
                println("[$] MAX INDEX: 0 -> ${maxOf(text.length - 1, 0)}")
                println("[$] AT INDEX MIN: ${text.getOrElse(selection.min) { '⚠' }}")
                println("[$] AT INDEX MAX: ${text.getOrElse(selection.max) { '⚠' }}")
            }

            else -> {}
        }

        equation = TextFieldValue(text.toString(), selection)
        return success
    }
}

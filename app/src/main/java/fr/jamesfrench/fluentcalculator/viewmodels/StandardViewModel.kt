package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.ViewModel
import fr.jamesfrench.fluentcalculator.classes.Action

class StandardViewModel : ViewModel() {
    var equation = TextFieldState("")

    fun executeKeyboardAction(action: Action, value: String = ""): Boolean {
        var success = false
        equation.edit {
            when (action) {
                Action.Append -> {
                    replace(selection.min, selection.max, value)
                    selection = TextRange(selection.max)
                    success = true
                }

                Action.Backspace -> {
                    if (selection.length > 0 || selection.min > 0) {
                        val offset =
                            if (selection.length > 0) 0 else 1 // Offset if no selection to remove previous character

                        delete(selection.min - offset, selection.max)
                    } // Return if nothing can be deleted
                }

                Action.ClearAll -> {
                    if (length > 0) {
                        delete(0, length)
                        success = true
                    }
                }

                Action.Equal -> {
                    println("[$] ${"-".repeat(20)} SELECTION REPORT ${"-".repeat(20)}")
                    println(
                        "[$] ${
                            StringBuilder(this.originalText)
                                .insert(selection.min, "[")
                                .insert(selection.max + 1, "]")
                        }"
                    )
                    println("[$] SELECT INDEX: ${selection.min} -> ${selection.max}")
                    println("[$] MAX INDEX: 0 -> ${maxOf(length - 1, 0)}")
                    println("[$] AT INDEX MIN: ${this.originalText.getOrElse(selection.min) { '⚠' }}")
                    println("[$] AT INDEX MAX: ${this.originalText.getOrElse(selection.max) { '⚠' }}")
                }

                else -> {}
            }
        }
        return success
    }
}

package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.ViewModel
import fr.jamesfrench.fluentcalculator.classes.Action

class StandardViewModel : ViewModel() {
    var equation by mutableStateOf(TextFieldState(""))

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
                    if (selection.length > 0) {
                        delete(selection.min, selection.max)
                        success = true
                    } else {
                        if (selection.min > 0) {
                            delete(selection.min - 1, selection.min)
                            success = true
                        }
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
                    println("[$] MAX INDEX: 0 -> ${maxOf(this.originalText.length - 1, 0)}")
                    println("[$] AT INDEX MIN: ${this.originalText.getOrElse(selection.min) { '⚠' }}")
                    println("[$] AT INDEX MAX: ${this.originalText.getOrElse(selection.max) { '⚠' }}")
                }

                else -> {}
            }
        }
        return success
    }
}

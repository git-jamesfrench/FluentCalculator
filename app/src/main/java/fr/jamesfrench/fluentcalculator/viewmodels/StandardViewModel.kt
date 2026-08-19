package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.lifecycle.ViewModel
import fr.jamesfrench.fluentcalculator.classes.Action

class StandardViewModel : ViewModel() {
    private val equation: String = ""

    fun executeKeyboardAction(action: Action, value: String? = null) {
        println(action)
        println(value)
    }
}
package fr.jamesfrench.fluentcalculator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jamesfrench.fluentcalculator.data.ObjectBox.store
import fr.jamesfrench.fluentcalculator.data.stores.Theme
import fr.jamesfrench.fluentcalculator.styling.styles.nothingTheme
import fr.jamesfrench.fluentcalculator.styling.styles.nothingThemeLight
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {
    val themesBox = store.boxFor(Theme::class.java)
    var currentTheme: Theme? = null

    fun initThemes() {
        val themes = themesBox.all

        if (themes.find { it.name == "Nothing Theme" } == null || false) {
            themesBox.put(nothingTheme())
        }
        if (themes.find { it.name == "Nothing Theme Light" } == null || false) {
            themesBox.put(nothingThemeLight())
        }
    }

    fun loadTheme() {
        val theme = themesBox.all.find { it.name == "Nothing Theme" } // Hardcoded selected theme
        currentTheme = theme
    }

    fun deleteEverything() {
        themesBox.query().build().remove()
    }

    init {
        viewModelScope.launch {
            initThemes()
            loadTheme()
        }
    }
}
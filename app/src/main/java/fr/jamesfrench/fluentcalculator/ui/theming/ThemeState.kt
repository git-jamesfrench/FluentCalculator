package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow

@Stable
class ThemeState(
    initialMode: ThemeMode = ThemeMode.SYSTEM,
    initialFamily: ThemeFamily = Themes.Default,
    private val store: ThemeStore? = null,
) {
    var mode: ThemeMode by mutableStateOf(initialMode)

    var family: ThemeFamily by mutableStateOf(initialFamily)

    var isHydrated: Boolean by mutableStateOf(store == null)
        private set

    internal fun hydrate(mode: ThemeMode, family: ThemeFamily) {
        this.mode = mode
        this.family = family
        isHydrated = true
    }

    internal fun markHydrated() {
        isHydrated = true
    }

    fun resolveTheme(systemInDarkTheme: Boolean): Theme =
        family.resolve(mode.resolve(systemInDarkTheme))
}

@Composable
fun rememberThemeState(store: ThemeStore? = null): ThemeState {
    val state = remember(store) { ThemeState(store = store) }
    if (store != null) {
        LaunchedEffect(store) {
            val saved = store.load()
            if (saved != null) {
                state.hydrate(saved.mode, Themes.byIdOrDefault(saved.familyId))
            } else {
                state.markHydrated()
            }
            snapshotFlow { ThemePrefs(state.mode, state.family.id) }
                .collect { store.save(it) }
        }
    }
    return state
}

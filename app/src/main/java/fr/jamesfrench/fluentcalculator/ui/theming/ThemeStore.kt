package fr.jamesfrench.fluentcalculator.ui.theming

import android.content.Context
import androidx.compose.runtime.Immutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

@Immutable
data class ThemePrefs(
    val mode: ThemeMode = ThemeMode.SYSTEM,
    val familyId: String = Themes.Default.id,
)

interface ThemeStore {
    suspend fun load(): ThemePrefs?

    suspend fun save(prefs: ThemePrefs)
}

class InMemoryThemeStore(
    initial: ThemePrefs? = null,
) : ThemeStore {
    private val mutex = Mutex()
    private var current: ThemePrefs? = initial

    override suspend fun load(): ThemePrefs? = mutex.withLock { current }

    override suspend fun save(prefs: ThemePrefs) {
        mutex.withLock { current = prefs }
    }
}

class SharedPreferencesThemeStore(
    context: Context,
    private val prefsName: String = DEFAULT_PREFS_NAME,
) : ThemeStore {
    private val applicationContext: Context = context.applicationContext

    override suspend fun load(): ThemePrefs? = withContext(Dispatchers.IO) {
        val prefs = applicationContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val familyId = prefs.getString(KEY_FAMILY, null) ?: return@withContext null
        val mode = prefs.getString(KEY_MODE, null)
            ?.let { stored -> runCatching { ThemeMode.valueOf(stored) }.getOrNull() }
            ?: ThemeMode.SYSTEM
        ThemePrefs(mode = mode, familyId = familyId)
    }

    override suspend fun save(prefs: ThemePrefs) {
        withContext(Dispatchers.IO) {
            applicationContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_FAMILY, prefs.familyId)
                .putString(KEY_MODE, prefs.mode.name)
                .apply()
        }
    }

    companion object {
        const val DEFAULT_PREFS_NAME = "fluent_theme"

        private const val KEY_FAMILY = "family_id"
        private const val KEY_MODE = "mode"
    }
}

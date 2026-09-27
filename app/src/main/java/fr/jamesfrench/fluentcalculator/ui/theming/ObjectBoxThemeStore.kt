package fr.jamesfrench.fluentcalculator.ui.theming

import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Entity
data class ThemePrefsEntity(
    @Id var id: Long = SINGLETON_ID,
    var mode: String = ThemeMode.SYSTEM.name,
    var familyId: String = Themes.Default.id,
) {
    companion object {
        const val SINGLETON_ID = 1L
    }
}

class ObjectBoxThemeStore(
    private val store: BoxStore,
) : ThemeStore {

    override suspend fun load(): ThemePrefs? = withContext(Dispatchers.IO) {
        val entity = store.boxFor(ThemePrefsEntity::class.java)
            .get(ThemePrefsEntity.SINGLETON_ID)
            ?: return@withContext null // fresh install, nothing persisted yet

        ThemePrefs(
            mode = runCatching { ThemeMode.valueOf(entity.mode) }
                .getOrNull() ?: ThemeMode.SYSTEM,
            familyId = entity.familyId.ifBlank { Themes.Default.id },
        )
    }

    override suspend fun save(prefs: ThemePrefs) {
        withContext(Dispatchers.IO) {
            store.boxFor(ThemePrefsEntity::class.java).put(
                ThemePrefsEntity(
                    mode = prefs.mode.name,
                    familyId = prefs.familyId,
                )
            )
        }
    }
}

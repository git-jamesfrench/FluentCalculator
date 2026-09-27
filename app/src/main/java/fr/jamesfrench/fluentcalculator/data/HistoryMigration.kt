package fr.jamesfrench.fluentcalculator.data

import android.content.Context
import io.objectbox.Box
import org.json.JSONArray
import org.json.JSONException

object HistoryMigration {

    private const val PREFS_NAME = "fluent_calculator_prefs"
    private const val KEY_HISTORY = "history"

    fun migrate(context: Context, box: Box<HistoryEntry>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_HISTORY, null) ?: return

        try {
            val array = JSONArray(raw)
            val now = System.currentTimeMillis()
            val entries = (0 until array.length()).mapNotNull { i ->
                val obj = array.optJSONObject(i) ?: return@mapNotNull null
                HistoryEntry(
                    equation = obj.optString("equation", ""),
                    result = obj.optString("result", ""),
                    timestamp = now - i
                )
            }.filter { it.equation.isNotBlank() && it.result.isNotBlank() }

            if (entries.isNotEmpty()) box.put(entries)
        } catch (_: JSONException) {
            // Malformed legacy payload: nothing to recover.
        } finally {
            prefs.edit().remove(KEY_HISTORY).apply()
        }
    }
}

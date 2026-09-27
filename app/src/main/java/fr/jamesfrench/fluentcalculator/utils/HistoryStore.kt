package fr.jamesfrench.fluentcalculator.utils

import android.content.Context
import androidx.core.content.edit
import fr.jamesfrench.fluentcalculator.classes.HistoryEntry
import org.json.JSONArray
import org.json.JSONObject

private const val PREFS_NAME = "fluent_calculator_prefs"
private const val KEY_HISTORY = "history"
private const val MAX_HISTORY_SIZE = 100

object HistoryStore {
    fun load(context: Context): MutableList<HistoryEntry> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_HISTORY, null) ?: return mutableListOf()

        return try {
            val array = JSONArray(raw)
            val list = mutableListOf<HistoryEntry>()

            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    HistoryEntry(
                        id = obj.getLong("id"),
                        equation = obj.getString("equation"),
                        result = obj.getString("result")
                    )
                )
            }

            list
        } catch (_: Exception) {
            mutableListOf()
        }
    }

    fun save(context: Context, history: List<HistoryEntry>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val array = JSONArray()

        history.take(MAX_HISTORY_SIZE).forEach { entry ->
            array.put(
                JSONObject().apply {
                    put("id", entry.id)
                    put("equation", entry.equation)
                    put("result", entry.result)
                }
            )
        }

        prefs.edit { putString(KEY_HISTORY, array.toString()) }
    }
}

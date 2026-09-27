package fr.jamesfrench.fluentcalculator.data

import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class HistoryRepository(private val box: Box<HistoryEntry>) : AutoCloseable {

    private val query: Query<HistoryEntry> = box.query()
        .orderDesc(HistoryEntry_.timestamp)
        .build()

    private val _history = MutableStateFlow(query.find(0, MAX_HISTORY_SIZE.toLong()))
    val history: StateFlow<List<HistoryEntry>> = _history.asStateFlow()

    private val subscription: DataSubscription = box.store
        .subscribe(HistoryEntry::class.java)
        .observer { _history.value = query.find(0, MAX_HISTORY_SIZE.toLong()) }

    suspend fun add(equation: String, result: String) {
        withContext(Dispatchers.IO) {
            box.put(
                HistoryEntry(
                    equation = equation,
                    result = result,
                    timestamp = System.currentTimeMillis()
                )
            )
            trim()
        }
    }

    suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) { box.remove(id) }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) { box.removeAll() }
    }

    private fun trim() {
        val all = box.query().orderDesc(HistoryEntry_.timestamp).build()
        try {
            all.findIds().drop(MAX_HISTORY_SIZE).forEach { box.remove(it) }
        } finally {
            all.close()
        }
    }

    override fun close() {
        subscription.cancel()
        query.close()
    }

    companion object {
        const val MAX_HISTORY_SIZE = 100
    }
}

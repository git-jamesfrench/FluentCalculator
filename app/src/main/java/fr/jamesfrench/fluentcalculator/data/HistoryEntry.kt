package fr.jamesfrench.fluentcalculator.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class HistoryEntry(
    @Id var id: Long = 0,
    var equation: String = "",
    var result: String = "",
    var timestamp: Long = 0
)

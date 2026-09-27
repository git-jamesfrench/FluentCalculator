package fr.jamesfrench.fluentcalculator.data.stores

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class Color(
    @Id var id: Long = 0,
    var name: String = "",
    var value: Long = 0xFF000000
) {
    lateinit var theme: ToOne<Theme>
}
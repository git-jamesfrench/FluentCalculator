package fr.jamesfrench.fluentcalculator.data.stores

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class Theme(
    @Id(assignable = true) var id: Long = 0,
    @Unique(onConflict = ConflictStrategy.REPLACE) var name: String = "",
    var isSystem: Boolean = false,

    var isStatusBarLight: Boolean = false,
) {
    @Backlink(to = "theme")
    lateinit var colors: ToMany<Color>

    lateinit var background: ToOne<Color>
    lateinit var onBackground: ToOne<Color>
    lateinit var onBackgroundActive: ToOne<Color>
    lateinit var onBackgroundDisabled: ToOne<Color>
    lateinit var onBackgroundIgnored: ToOne<Color>

    lateinit var surface: ToOne<Color>
    lateinit var onSurface: ToOne<Color>

    lateinit var neutral: ToOne<Color>
    lateinit var activeNeutral: ToOne<Color>
    lateinit var onNeutral: ToOne<Color>

    lateinit var inverse: ToOne<Color>
    lateinit var activeInverse: ToOne<Color>
    lateinit var onInverse: ToOne<Color>

    lateinit var accent: ToOne<Color>
    lateinit var activeAccent: ToOne<Color>
    lateinit var onAccent: ToOne<Color>

    lateinit var error: ToOne<Color>
}
package fr.jamesfrench.fluentcalculator

import android.app.Application
import fr.jamesfrench.fluentcalculator.data.HistoryEntry
import fr.jamesfrench.fluentcalculator.data.HistoryMigration
import fr.jamesfrench.fluentcalculator.data.ObjectBox

class FluentCalculatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        HistoryMigration.migrate(
            this,
            ObjectBox.store.boxFor(HistoryEntry::class.java)
        )
    }
}

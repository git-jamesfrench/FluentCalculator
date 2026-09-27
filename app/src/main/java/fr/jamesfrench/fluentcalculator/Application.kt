package fr.jamesfrench.fluentcalculator

import android.app.Application
import fr.jamesfrench.fluentcalculator.data.ObjectBox

class FluentCalculator : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}
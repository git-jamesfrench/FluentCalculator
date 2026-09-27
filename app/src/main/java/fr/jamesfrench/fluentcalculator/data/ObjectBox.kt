package fr.jamesfrench.fluentcalculator.data

import android.content.Context
import android.util.Log
import fr.jamesfrench.fluentcalculator.data.stores.MyObjectBox
import fr.jamesfrench.fluentcalculator.viewmodels.DEBUG
import io.objectbox.BoxStore
import io.objectbox.android.Admin


object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context)
            .build()

        if (DEBUG) { // ObjectBox Admin
            val started = Admin(store).start(context)
            Log.i("ObjectBoxAdmin", "Started: $started")
        }
    }
}
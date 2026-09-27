package fr.jamesfrench.fluentcalculator.data

import android.content.Context
import io.objectbox.BoxStore

object ObjectBox {

    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        if (::store.isInitialized) return
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}

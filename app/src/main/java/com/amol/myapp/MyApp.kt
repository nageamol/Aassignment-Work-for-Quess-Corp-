package com.amol.myapp

import android.app.Application
import android.graphics.Typeface
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}
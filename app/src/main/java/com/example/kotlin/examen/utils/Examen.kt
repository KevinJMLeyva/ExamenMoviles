package com.example.kotlin.examen.utils

import android.app.Application
import com.example.kotlin.wushuapp.data.network.NetworkModuleDI

class Examen : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkModuleDI.initializeParse(this)
    }
}

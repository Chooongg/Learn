package com.chooongg.core

import android.app.Application

open class LearnBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    open fun onCreateMainProcess() = Unit
    open fun onCreateOtherProcess() = Unit
}
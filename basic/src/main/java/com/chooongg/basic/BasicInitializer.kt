package com.chooongg.basic

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.chooongg.basic.ext.BoxLog
import com.chooongg.basic.ext.isAppDebug
import com.chooongg.basic.ext.setNightMode
import com.facebook.stetho.Stetho
import com.tencent.mmkv.MMKV

@Suppress("unused")
class BasicInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        ApplicationManager.initialize(context as Application)
        MMKV.initialize(context)
        setNightMode(LearnMMKV.DayNightMode.get())
        BoxLog.setEnable(isAppDebug())
        Stetho.initializeWithDefaults(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
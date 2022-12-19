package com.chooongg.basic

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.chooongg.basic.ext.logDClass
import com.chooongg.basic.ext.setNightMode
import com.facebook.stetho.Stetho
import com.tencent.mmkv.MMKV

@Keep
class BasicInitializer : Initializer<String> {
    override fun create(context: Context): String {
        ApplicationManager.initialize(context as Application)
        MMKV.initialize(context)
        setNightMode(LearnMMKV.DayNightMode.get())
        Stetho.initializeWithDefaults(context)
        logDClass("Learn", BasicInitializer::class.java, "Created")
        return "Created"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
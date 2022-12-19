package com.chooongg.basic

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatDelegate
import androidx.startup.Initializer
import com.chooongg.basic.ext.logDClass
import com.chooongg.basic.ext.setNightMode
import com.facebook.stetho.Stetho
import com.tencent.mmkv.MMKV

@Keep
class BasicInitializer : Initializer<String> {
    override fun create(context: Context): String {
        ApplicationManager.initialize(context as Application)
        val sp = context.getSharedPreferences("chooongg_learn", Context.MODE_PRIVATE)
        setNightMode(sp.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO))
        MMKV.initialize(context)
        Stetho.initializeWithDefaults(context)
        logDClass("Learn", BasicInitializer::class.java, "Created")
        return "Created"
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
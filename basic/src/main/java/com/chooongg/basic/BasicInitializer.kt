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
class BasicInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Stetho.initializeWithDefaults(context)
        ApplicationManager.initialize(context as Application)
        MMKV.initialize(context)
        setNightMode(
            context.getSharedPreferences("chooongg-learn", Context.MODE_PRIVATE)
                .getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )
        logDClass(BasicInitializer::class.java, "created")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
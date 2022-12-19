package com.chooongg.basic.ext

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.basic.APPLICATION
import com.chooongg.basic.LearnMMKV

/**
 * 判断当前是否深色模式
 */
fun Context.isNightMode(): Boolean {
    val isNight = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
    return isNight
}

/**
 * 获取当前的夜间模式
 */
fun getNightMode() = AppCompatDelegate.getDefaultNightMode()

/**
 * 设置深色模式
 */
fun setNightMode(@AppCompatDelegate.NightMode mode: Int) {
    LearnMMKV.DayNightMode.set(mode)
    AppCompatDelegate.setDefaultNightMode(mode)
}
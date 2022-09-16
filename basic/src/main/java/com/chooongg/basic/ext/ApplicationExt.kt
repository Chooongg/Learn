package com.chooongg.basic.ext

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.ApplicationInfoFlags
import android.os.Build
import com.chooongg.basic.APPLICATION

/**
 * 判断是否是 Debug 版本
 *
 * @param packageName 包名-默认本App包名
 */
@Suppress("DEPRECATION")
fun isAppDebug(packageName: String = APPLICATION.packageName): Boolean {
    if (packageName.isBlank()) return false
    return try {
        val ai = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            APPLICATION.packageManager.getApplicationInfo(packageName, ApplicationInfoFlags.of(0))
        } else {
            APPLICATION.packageManager.getApplicationInfo(packageName, 0)
        }
        ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * Debug 才运行的代码块
 */
fun debug(init: () -> Unit) = if (isAppDebug()) init() else Unit

/**
 * Release 才运行的代码块
 */
fun release(init: () -> Unit) = if (!isAppDebug()) init() else Unit
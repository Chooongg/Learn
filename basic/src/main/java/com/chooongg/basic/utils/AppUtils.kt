package com.chooongg.basic.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.chooongg.basic.APPLICATION
import com.chooongg.basic.ext.activityManager

object AppUtils {

    @Suppress("DEPRECATION")
    fun getAppVersionName(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            APPLICATION.packageManager.getPackageInfo(
                APPLICATION.packageName, PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            APPLICATION.packageManager.getPackageInfo(APPLICATION.packageName, 0)
        }.versionName
    }

    @Suppress("DEPRECATION")
    fun getAppVersionCode(): Long {
        val info = APPLICATION.packageManager.getPackageInfo(APPLICATION.packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode
        } else {
            info.versionCode.toLong()
        }
    }

    /**
     * 打开应用的详细信息设置
     */
    fun gotoAppDetailsSettings(
        context: Context, requestCode: Int, packageName: String = context.packageName
    ) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        if (context is Activity) context.startActivityForResult(intent, requestCode)
    }

    /**
     * 打开指定包名的应用
     */
    fun startActivityByPackage(context: Context, packageName: String): Boolean {
        val pm = context.packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        if (null != intent) {
            context.startActivity(intent)
            return true
        }
        return false
    }

    /**
     * 获取当前进程的名称，默认进程名称是包名
     */
    val currentProcessName: String?
        get() {
            val pid = android.os.Process.myPid()
            for (appProcess in APPLICATION.activityManager.runningAppProcesses) {
                if (appProcess.pid == pid) return appProcess.processName
            }
            return null
        }
}
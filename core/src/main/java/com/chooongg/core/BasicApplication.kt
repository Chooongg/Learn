package com.chooongg.core

import android.app.Application
import com.chooongg.basic.utils.AppUtils

open class BasicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val processName = AppUtils.currentProcessName
        if (processName == packageName) {
            onCreateMainProcess()
        } else if (processName != null) {
            onCreateOtherProcess(processName)
        }
    }

    /**
     * 主线程初始化的
     */
    open fun onCreateMainProcess() = Unit

    /**
     * 其他线程初始化方法
     */
    open fun onCreateOtherProcess(processName: String) = Unit
}
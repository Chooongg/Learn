package com.chooongg.core

import android.app.Application
import androidx.lifecycle.ProcessLifecycleInitializer
import androidx.lifecycle.ProcessLifecycleOwner
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
     * 只在
     */
    open fun onCreateMainProcess() = Unit

    open fun onCreateOtherProcess(processName: String) = Unit
}
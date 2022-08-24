package com.chooongg.core

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.chooongg.basic.ext.logDClass
import com.chooongg.core.permission.PermissionInterceptor
import com.hjq.permissions.XXPermissions

@Keep
class CoreInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        XXPermissions.setInterceptor(PermissionInterceptor())
        logDClass(CoreInitializer::class.java, "created")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
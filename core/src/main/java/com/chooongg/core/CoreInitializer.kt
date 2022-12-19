package com.chooongg.core

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.chooongg.basic.BasicInitializer
import com.chooongg.basic.ext.logDClass
import com.chooongg.core.permission.PermissionInterceptor
import com.chooongg.core.widget.BasicLoadMoreView
import com.hjq.permissions.XXPermissions

@Keep
class CoreInitializer : Initializer<String> {
    override fun create(context: Context): String {
        XXPermissions.setInterceptor(PermissionInterceptor())
        LoadMoreModuleConfig.defLoadMoreView = BasicLoadMoreView()
        logDClass("Learn", CoreInitializer::class.java, "Created")
        return "Created"
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> =
        mutableListOf(BasicInitializer::class.java)
}
package com.chooongg.core.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.logDTag
import com.chooongg.core.annotation.ActivityEdgeToEdge
import com.chooongg.core.annotation.Theme
import com.chooongg.core.ext.getAnnotationTitle

abstract class BasicActivity : AppCompatActivity() {

    inline val context: Context get() = this
    inline val activity: BasicActivity get() = this

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initContent(savedInstanceState: Bundle?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        logDTag("ACTIVITY", "${javaClass.simpleName}(${title}) onCreate")
        javaClass.getAnnotation(Theme::class.java)?.value?.let { setTheme(it) }
        configEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentViewInternal()
        javaClass.getAnnotationTitle(context)?.let { title = it }
        initView(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initContent(savedInstanceState)
    }

    internal open fun setContentViewInternal() = Unit

    private fun configEdgeToEdge() {
        javaClass.getAnnotation(ActivityEdgeToEdge::class.java)?.let {
            if (it.isEdgeToEdge.not()) return
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = it.statusBarColor
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logDTag("ACTIVITY", "${javaClass.simpleName}(${title}) onDestroy")
    }
}
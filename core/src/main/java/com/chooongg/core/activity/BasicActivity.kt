package com.chooongg.core.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.chooongg.basic.ext.contentView
import com.chooongg.basic.ext.hideIME
import com.chooongg.basic.ext.logDClass
import com.chooongg.core.annotation.ActivityEdgeToEdge
import com.chooongg.core.annotation.Theme
import com.chooongg.core.ext.getAnnotationTitle
import com.chooongg.core.viewModel.BasicModel

abstract class BasicActivity : AppCompatActivity() {

    inline val context: Context get() = this
    inline val activity: BasicActivity get() = this

    @LayoutRes
    protected abstract fun initLayout(): Int

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initContent(savedInstanceState: Bundle?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        javaClass.getAnnotation(Theme::class.java)?.value?.let { setTheme(it) }
        configEdgeToEdge()
        super.onCreate(savedInstanceState)
        javaClass.getAnnotationTitle(context)?.let { title = it }
        setContentViewInternal()
        initView(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logDClass(javaClass, "(${title}) onCreated")
        super.onPostCreate(savedInstanceState)
        contentView.setOnClickListener {
            it.clearFocus()
            hideIME()
        }
        initContent(savedInstanceState)
    }

    internal open fun setContentViewInternal() {
        super.setContentView(initLayout())
    }

    override fun setContentView(layoutResID: Int) {
        if (layoutResID != ResourcesCompat.ID_NULL) super.setContentView(layoutResID)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) = Unit

    private fun configEdgeToEdge() {
        javaClass.getAnnotation(ActivityEdgeToEdge::class.java)?.let {
            if (it.isEdgeToEdge.not()) return
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = it.statusBarColor
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logDClass(javaClass, "(${title}) onDestroy")
    }
}
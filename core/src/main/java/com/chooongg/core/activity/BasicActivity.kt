package com.chooongg.core.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.chooongg.basic.ACTIVITY_TASK
import com.chooongg.basic.ext.contentView
import com.chooongg.basic.ext.hideIME
import com.chooongg.basic.ext.logDClass
import com.chooongg.core.R
import com.chooongg.core.annotation.ActivityEdgeToEdge
import com.chooongg.core.annotation.AutoBackPressed
import com.chooongg.core.annotation.HomeButton
import com.chooongg.core.annotation.Theme
import com.chooongg.core.ext.getAnnotationTitle
import com.chooongg.core.fragment.BasicFragment
import com.chooongg.core.widget.TopAppBar
import com.google.android.material.snackbar.Snackbar

@HomeButton
@AutoBackPressed
abstract class BasicActivity : AppCompatActivity() {

    inline val context: Context get() = this
    inline val activity: BasicActivity get() = this

    @LayoutRes
    protected abstract fun initLayout(): Int

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initContent(savedInstanceState: Bundle?) = Unit

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        javaClass.getAnnotation(Theme::class.java)?.value?.let { setTheme(it) }
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
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

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        if (toolbar == null) return
        if (javaClass.getAnnotation(HomeButton::class.java)?.isShow == true) {
            supportActionBar?.let {
                it.setHomeButtonEnabled(true)
                it.setDisplayHomeAsUpEnabled(true)
                if (toolbar is TopAppBar) {
                    when (toolbar.navigationType) {
                        TopAppBar.TYPE_NAVIGATION_CLOSE -> it.setHomeAsUpIndicator(R.drawable.ic_top_app_bar_close)
                        else -> it.setHomeAsUpIndicator(R.drawable.ic_top_app_bar_back)
                    }
                    return
                } else {
                    it.setHomeAsUpIndicator(R.drawable.ic_top_app_bar_back)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var firstTime: Long = 0
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BasicFragment && !it.isHidden && it.isResumed && it.onBackPressedIntercept()) {
                return
            }
        }
        if (javaClass.getAnnotation(AutoBackPressed::class.java)?.value == true && ACTIVITY_TASK.size <= 1) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                Snackbar.make(contentView, "再按一次退出程序", Snackbar.LENGTH_SHORT).show()
                firstTime = secondTime
                return
            }
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        logDClass(javaClass, "(${title}) onDestroy")
    }
}
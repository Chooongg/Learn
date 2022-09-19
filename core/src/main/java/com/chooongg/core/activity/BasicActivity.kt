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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.chooongg.basic.ACTIVITY_TASK
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.contentView
import com.chooongg.basic.ext.hideIME
import com.chooongg.basic.ext.logDClass
import com.chooongg.core.R
import com.chooongg.core.annotation.*
import com.chooongg.core.ext.EXTRA_TRANSITION_NAME
import com.chooongg.core.ext.getAnnotationTitle
import com.chooongg.core.fragment.BasicFragment
import com.chooongg.core.widget.TopAppBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@HomeButton
@AutoBackPressed
@ActivityEdgeToEdge
@ActivityTransitions
abstract class BasicActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    inline val context: Context get() = this
    inline val activity: BasicActivity get() = this

    @LayoutRes
    protected abstract fun initLayout(): Int

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initContent(savedInstanceState: Bundle?) = Unit

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        javaClass.getAnnotation(Theme::class.java)?.value?.let { setTheme(it) }
        window.setBackgroundDrawable(null)
        configContainerTransform()
        super.onCreate(savedInstanceState)
        configEdgeToEdge()
        javaClass.getAnnotationTitle(context)?.let { title = it }
        setContentViewInternal()
        initView(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logDClass(javaClass, buildString {
            if (title != null) append('(').append(title).append(") ")
            append("onCreated")
        })
        super.onPostCreate(savedInstanceState)
        contentView.setOnClickListener {
            it.clearFocus()
            hideIME()
        }
        initContent(savedInstanceState)
    }

    protected open fun setContentViewInternal() {
        super.setContentView(initLayout())
    }

    override fun setContentView(layoutResID: Int) {
        if (layoutResID != ResourcesCompat.ID_NULL) super.setContentView(layoutResID)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) = Unit

    private fun configContainerTransform() {
        if (javaClass.getAnnotation(ActivityTransitions::class.java)?.enable != true) return
        window.apply {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            exitTransition = null
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            reenterTransition = null
        }
        window.sharedElementsUseOverlay = false
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
        if (transitionName != null) {
            contentView.transitionName = transitionName
            setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            window.sharedElementEnterTransition = buildContainerTransform(true)
            window.sharedElementReturnTransition = buildContainerTransform(false)
        }
    }

    protected open fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
        transform.containerColor = attrColor(com.google.android.material.R.attr.colorSurface)
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        return transform
    }

    private fun configEdgeToEdge() {
        javaClass.getAnnotation(ActivityEdgeToEdge::class.java)?.let {
            if (it.isEdgeToEdge.not()) return
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = it.statusBarColor
            val left = it.fitsSide and ActivityEdgeToEdge.LEFT != 0
            val top = it.fitsSide and ActivityEdgeToEdge.TOP != 0
            val right = it.fitsSide and ActivityEdgeToEdge.RIGHT != 0
            val bottom = it.fitsSide and ActivityEdgeToEdge.BOTTOM != 0
            if (left || top || right || bottom) {
                ViewCompat.setOnApplyWindowInsetsListener(contentView) { view, insets ->
                    val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    view.setPadding(
                        if (left) systemBarInsets.left else 0,
                        if (top) systemBarInsets.top else 0,
                        if (right) systemBarInsets.right else 0,
                        if (bottom) systemBarInsets.bottom else 0
                    )
                    insets
                }
            }
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
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun clearTransitionAnimation() {
        contentView.transitionName = null
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
                Snackbar.make(contentView, "再按一次退出应用", Snackbar.LENGTH_SHORT).show()
                firstTime = secondTime
                return
            }
        }
        super.onBackPressed()
    }

    fun clearTransition() {
        contentView.transitionName = null
    }

    override fun onDestroy() {
        super.onDestroy()
        logDClass(javaClass, buildString {
            if (title != null) append('(').append(title).append(") ")
            append("onDestroy")
        })
    }
}
package com.chooongg.core.activity

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.chooongg.basic.ext.*
import com.chooongg.core.R
import com.chooongg.core.annotation.ActivityEdgeToEdge
import com.chooongg.core.annotation.ActivityTransitions
import com.chooongg.core.annotation.NavigationButton
import com.chooongg.core.annotation.Theme
import com.chooongg.core.ext.EXTRA_TRANSITION_NAME
import com.chooongg.core.ext.TRANSITION_NAME_CONTAINER_TRANSFORM
import com.chooongg.core.ext.getAnnotationTitle
import com.chooongg.core.fragment.BasicFragment
import com.google.android.material.motion.MotionUtils
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@NavigationButton
@ActivityTransitions
@ActivityEdgeToEdge(true)
abstract class BasicActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    inline val context: Context get() = this
    inline val activity: BasicActivity get() = this

    @LayoutRes
    protected abstract fun initLayout(): Int

    protected open fun initView(savedInstanceState: Bundle?) {}

    protected open fun initContent(savedInstanceState: Bundle?) {}

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        javaClass.getAnnotation(Theme::class.java)?.value?.let { setTheme(it) }
        configContainerTransform()
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                supportFragmentManager.fragments.forEach {
                    if (it is BasicFragment && !it.isHidden && it.isResumed && it.onBackPressedIntercept()) return
                }
                finishAfterTransition()
            }
        })
        configEdgeToEdge()
        javaClass.getAnnotationTitle(context)?.let { title = it }
        setContentViewInternal()
        window.setBackgroundDrawable(null)
        initView(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logDClass("Activity", javaClass, buildString {
            if (title != null) append('[').append(title).append("] ")
            append("onCreated")
        })
        super.onPostCreate(savedInstanceState)
        initContent(savedInstanceState)
    }

    protected open fun setContentViewInternal() {
        super.setContentView(initLayout())
    }

    override fun setContentView(layoutResID: Int) {
        if (layoutResID != ResourcesCompat.ID_NULL) super.setContentView(layoutResID)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) = Unit

    /**
     * 配置过渡动画
     * @return true: 使用配置, false: 使用默认
     */
    protected open fun configTransition() = false

    private fun configContainerTransform() {
        if (javaClass.getAnnotation(ActivityTransitions::class.java)?.enable != true) return
        with(window) {
            sharedElementsUseOverlay = false
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            if (configTransition()) return@with
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            exitTransition = null
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            reenterTransition = null
            transitionBackgroundFadeDuration = MotionUtils.resolveThemeDuration(
                context, com.google.android.material.R.attr.motionDurationLong1, -1
            ).toLong()
        }
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
        if (transitionName == TRANSITION_NAME_CONTAINER_TRANSFORM) {
            contentView.transitionName = transitionName
            setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            window.sharedElementEnterTransition = buildContainerTransform(true)
            window.sharedElementReturnTransition = buildContainerTransform(false)
        }
    }

    protected open fun buildContainerTransform(entering: Boolean): MaterialContainerTransform {
        val transform = MaterialContainerTransform(this, entering)
        transform.addTarget(android.R.id.content)
        transform.fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        transform.pathMotion = MaterialArcMotion()
        return transform
    }

    private fun configEdgeToEdge() {
        if (attrBoolean(androidx.appcompat.R.attr.windowActionBar, false)) {
            return
        }
        javaClass.getAnnotation(ActivityEdgeToEdge::class.java)?.let {
            if (it.isEdgeToEdge.not()) return
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val left = it.fitsSide and ActivityEdgeToEdge.LEFT != 0
            val top = it.fitsSide and ActivityEdgeToEdge.TOP != 0
            val right = it.fitsSide and ActivityEdgeToEdge.RIGHT != 0
            val bottom = it.fitsSide and ActivityEdgeToEdge.BOTTOM != 0
            if (left || top || right || bottom) {
                ViewCompat.setOnApplyWindowInsetsListener(contentView) { view, insets ->
                    val barInsets = insets.getInsets(
                        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
                    )
                    view.setPadding(
                        if (left) barInsets.left else 0,
                        if (top) barInsets.top else 0,
                        if (right) barInsets.right else 0,
                        if (bottom) barInsets.bottom else 0
                    )
                    insets
                }
            }
        }
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        if (toolbar == null) return
        val navigationButton = javaClass.getAnnotation(NavigationButton::class.java) ?: return
        if (navigationButton.isShow) {
            supportActionBar?.let {
                it.setHomeButtonEnabled(true)
                it.setDisplayHomeAsUpEnabled(true)
                when (navigationButton.iconType) {
                    NavigationButton.ICON_TYPE_BACK -> it.setHomeAsUpIndicator(R.drawable.ic_top_app_bar_back)
                    NavigationButton.ICON_TYPE_CLOSE -> it.setHomeAsUpIndicator(R.drawable.ic_top_app_bar_close)
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

    fun clearTransition() {
        contentView.transitionName = null
    }

    override fun onDestroy() {
        super.onDestroy()
        logDClass("Activity", javaClass, buildString {
            if (title != null) append('[').append(title).append("] ")
            append("onDestroy")
        })
    }
}
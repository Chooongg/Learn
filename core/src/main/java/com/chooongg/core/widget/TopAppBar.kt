package com.chooongg.core.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.chooongg.basic.ext.getActivity
import com.chooongg.basic.ext.multipleValid
import com.chooongg.core.R
import com.google.android.material.appbar.MaterialToolbar

class TopAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialToolbar(context, attrs, defStyleAttr) {

    companion object {
        const val TYPE_NAVIGATION_NONE = 0
        const val TYPE_NAVIGATION_BACK = 1
        const val TYPE_NAVIGATION_CLOSE = 2
    }

    @IntDef(TYPE_NAVIGATION_NONE, TYPE_NAVIGATION_BACK, TYPE_NAVIGATION_CLOSE)
    annotation class NavigationType

    private var mTitleTextColor: ColorStateList? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopAppBar, defStyleAttr, 0)
        setNavigationType(a.getInt(R.styleable.TopAppBar_navigationType, TYPE_NAVIGATION_NONE))
        a.recycle()
    }

    fun setNavigationType(@NavigationType type: Int) {
        when (type) {
            1 -> setNavigation(R.drawable.ic_top_app_bar_back) {
                if (multipleValid()) context.getActivity()?.onBackPressed()
            }
            2 -> setNavigation(R.drawable.ic_top_app_bar_back) {
                if (multipleValid()) context.getActivity()?.finish()
            }
            else -> setNavigation(null, null)
        }
    }

    fun setNavigation(@DrawableRes resId: Int, listener: ((View) -> Unit)?) {
        setNavigationIcon(resId)
        setNavigationOnClickListener(listener)
    }

    fun setNavigation(icon: Drawable?, listener: ((View) -> Unit)?) {
        navigationIcon = icon
        setNavigationOnClickListener(listener)
    }

    val titleTextColor get() = mTitleTextColor!!
    override fun setTitleTextColor(color: ColorStateList) {
        mTitleTextColor = color
        super.setTitleTextColor(color)
    }
}
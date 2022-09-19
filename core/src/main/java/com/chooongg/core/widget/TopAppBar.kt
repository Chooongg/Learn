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
    defStyleAttr: Int = androidx.appcompat.R.attr.toolbarStyle
) : MaterialToolbar(context, attrs, defStyleAttr) {

    companion object {
        const val TYPE_NAVIGATION_NONE = 0
        const val TYPE_NAVIGATION_BACK = 1
        const val TYPE_NAVIGATION_CLOSE = 2
    }

    @IntDef(TYPE_NAVIGATION_NONE, TYPE_NAVIGATION_BACK, TYPE_NAVIGATION_CLOSE)
    annotation class NavigationType

    @NavigationType
    var navigationType = TYPE_NAVIGATION_NONE
        set(value) {
            field = value
            when (field) {
                TYPE_NAVIGATION_BACK -> setNavigation(R.drawable.ic_top_app_bar_back) {
                    if (multipleValid()) context.getActivity()?.onBackPressed()
                }
                TYPE_NAVIGATION_CLOSE -> setNavigation(R.drawable.ic_top_app_bar_close) {
                    if (multipleValid()) context.getActivity()?.onBackPressed()
                }
                else -> setNavigation(null, null)
            }
        }

    /**
     * onBackPressed logic goes here. For instance:
     * Prevents closing the app to go home screen when in the
     * middle of entering data to a form
     * or from accidentally leaving a fragment with a WebView in it
     *
     * Unregistering the callback to stop intercepting the back gesture:
     * When the user transitions to the topmost screen (activity, fragment)
     * in the BackStack, unregister the callback by using
     * OnBackInvokeDispatcher.unregisterOnBackInvokedCallback
     * (https://developer.android.com/reference/kotlin/android/view/OnBackInvokedDispatcher#unregisteronbackinvokedcallback)
     */

    private var mTitleTextColor: ColorStateList? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopAppBar, defStyleAttr, 0)
        navigationType = a.getInt(R.styleable.TopAppBar_navigationType, TYPE_NAVIGATION_NONE)
        a.recycle()
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
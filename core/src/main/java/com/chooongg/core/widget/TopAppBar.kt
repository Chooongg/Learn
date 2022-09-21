package com.chooongg.core.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import com.google.android.material.appbar.MaterialToolbar

class TopAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.toolbarStyle
) : MaterialToolbar(context, attrs, defStyleAttr) {

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
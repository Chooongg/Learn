package com.chooongg.stateLayout.state

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.chooongg.stateLayout.StateLayout

abstract class AbstractState {

    internal lateinit var targetView: View

    private lateinit var context: Context

    internal fun obtainTargetView(context: Context) {
        this.context = context
        targetView = onBuildView(context)
        targetView.tag = StateLayout.STATE_ITEM_TAG
    }

    protected abstract fun onBuildView(context: Context): View

    abstract fun onAttach(view: View, message: CharSequence?)

    abstract fun onChangeMessage(view: View, message: CharSequence?)

    open fun getReloadEventView(view: View): View? = targetView

    open fun onDetach(view: View) = Unit

    open fun onDetach(view: View, removeBlock: () -> Unit) = Unit

    open fun getLayoutParams(): FrameLayout.LayoutParams =
        FrameLayout.LayoutParams(-2, -2, Gravity.CENTER)

    open fun isShowSuccess(): Boolean = false

    open fun isEnableShowAnimation(): Boolean = true

    open fun isEnableHideAnimation(): Boolean = true
}
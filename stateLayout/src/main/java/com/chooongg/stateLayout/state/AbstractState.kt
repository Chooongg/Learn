package com.chooongg.stateLayout.state

import android.content.Context
import android.view.View
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

    abstract fun onDetach(view: View)

    open fun isShowSuccess(): Boolean = false
}
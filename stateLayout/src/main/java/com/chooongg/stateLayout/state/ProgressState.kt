package com.chooongg.stateLayout.state

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.chooongg.basic.ext.dp2px
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressState : AbstractState() {
    override fun onBuildView(context: Context) = CircularProgressIndicator(context).apply {
        showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
        trackCornerRadius = dp2px(16f)
        indicatorSize = dp2px(56f)
        isIndeterminate = true
        hide()
    }

    override fun onAttach(view: View, message: CharSequence?) {
        (view as CircularProgressIndicator).show()
    }

    override fun onDetach(view: View, removeBlock: () -> Unit) {
        view.animate().cancel()
        view.animate().alpha(0f).withEndAction { removeBlock.invoke() }
    }

    override fun onChangeMessage(view: View, message: CharSequence?) {
    }

    override fun getLayoutParams(): FrameLayout.LayoutParams =
        FrameLayout.LayoutParams(-1, -1, Gravity.CENTER)

    override fun isEnableShowAnimation() = false

    override fun isEnableHideAnimation() = false
}
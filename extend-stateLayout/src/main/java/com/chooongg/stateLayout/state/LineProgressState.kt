package com.chooongg.stateLayout.state

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator

class LineProgressState : AbstractState() {
    override fun onBuildView(context: Context) = LinearProgressIndicator(context).apply {
        showAnimationBehavior = BaseProgressIndicator.SHOW_INWARD
        isIndeterminate = true
        hide()
    }

    override fun onAttach(view: View, message: CharSequence?) {
        (view as LinearProgressIndicator).show()
    }

    override fun onChangeMessage(view: View, message: CharSequence?) = Unit

    override fun onDetach(view: View, removeBlock: () -> Unit) {
        view.animate().cancel()
        view.animate().alpha(0f).withEndAction { removeBlock.invoke() }
    }

    override fun getLayoutParams(): FrameLayout.LayoutParams =
        FrameLayout.LayoutParams(-1, -2, Gravity.TOP)

    override fun isShowSuccess() = true

    override fun isEnableShowAnimation() = false
}
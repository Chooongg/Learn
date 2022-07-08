package com.chooongg.stateLayout.state

import android.content.Context
import android.content.res.Resources
import android.view.View
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

    override fun onChangeMessage(view: View, message: CharSequence?) {
    }

    override fun onDetach(view: View) {
    }

    private fun dp2px(dp: Float) =
        (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}
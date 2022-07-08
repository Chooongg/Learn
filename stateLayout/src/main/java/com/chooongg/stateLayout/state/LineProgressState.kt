package com.chooongg.stateLayout.state

import android.content.Context
import android.view.View
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator

class LineProgressState : AbstractState() {
    override fun onBuildView(context: Context) = LinearProgressIndicator(context).apply {
        showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
        isIndeterminate = true
        hide()
    }

    override fun onAttach(view: View, message: CharSequence?) {
        (view as LinearProgressIndicator).show()
    }

    override fun onChangeMessage(view: View, message: CharSequence?) = Unit

    override fun onDetach(view: View) = Unit

    override fun isShowSuccess() = true
}
package com.chooongg.stateLayout.animation

import android.view.View

object FadeStateLayoutAnimation : StateLayoutAnimation {
    override fun createAnimation(view: View) {
        view.alpha = 0f
    }

    override fun showAnimation(view: View) {
        view.animate().cancel()
        view.animate().alpha(1f)
    }

    override fun hideAnimation(view: View, removeViewBlock: () -> Unit) {
        view.animate().cancel()
        view.animate().alpha(0f).withEndAction {
            removeViewBlock.invoke()
        }
    }
}
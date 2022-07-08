package com.chooongg.stateLayout.animation

import android.view.View

interface StateLayoutAnimation {
    fun createAnimation(view: View)
    fun showAnimation(view: View)
    fun hideAnimation(view: View, removeViewBlock: () -> Unit)
}
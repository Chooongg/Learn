package com.chooongg.stateLayout.state

import android.content.Context
import android.view.View

class SuccessState : AbstractState() {
    override fun onBuildView(context: Context) = View(context)
    override fun onAttach(view: View, message: CharSequence?) = Unit
    override fun onChangeMessage(view: View, message: CharSequence?) = Unit
    override fun getReloadEventView(view: View): View? = null
    override fun onDetach(view: View) = Unit
}
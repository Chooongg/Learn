package com.chooongg.stateLayout

import com.chooongg.stateLayout.state.AbstractState
import kotlin.reflect.KClass

interface OnStateChangeListener {
    fun onStateChange(state: KClass<out AbstractState>)
}
package com.chooongg.stateLayout

import com.chooongg.stateLayout.animation.FadeStateLayoutAnimation
import com.chooongg.stateLayout.animation.StateLayoutAnimation
import com.chooongg.stateLayout.state.AbstractState
import com.chooongg.stateLayout.state.SuccessState
import kotlin.reflect.KClass

object StateLayoutManager {
    var defaultState: KClass<out AbstractState> = SuccessState::class
    var enableAnimation = true
    var animation: StateLayoutAnimation = FadeStateLayoutAnimation
}
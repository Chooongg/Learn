package com.chooongg.stateLayout

import com.chooongg.stateLayout.animation.FadeStateLayoutAnimation
import com.chooongg.stateLayout.animation.StateLayoutAnimation
import com.chooongg.stateLayout.state.AbstractState
import kotlin.reflect.KClass

object StateLayoutManager {
    var defaultStatus: KClass<out AbstractState>? = null
    var enableAnimation = true
    var animation: KClass<out StateLayoutAnimation> = FadeStateLayoutAnimation::class
}
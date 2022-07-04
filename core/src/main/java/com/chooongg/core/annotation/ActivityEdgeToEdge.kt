package com.chooongg.core.annotation

import android.graphics.Color
import androidx.annotation.ColorInt
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityEdgeToEdge(
    val isEdgeToEdge: Boolean = true,
    @ColorInt val statusBarColor: Int = Color.TRANSPARENT
)

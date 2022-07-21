package com.chooongg.core.annotation

import android.graphics.Color
import android.view.WindowInsets.Side
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityEdgeToEdge(
    val isEdgeToEdge: Boolean = true,
    @InsetsSide val fitsSide: Int = Side.BOTTOM,
    @ColorInt val statusBarColor: Int = Color.TRANSPARENT
) {
    @IntDef(flag = true, value = [Side.TOP, Side.BOTTOM, Side.LEFT, Side.RIGHT])
    annotation class InsetsSide
}
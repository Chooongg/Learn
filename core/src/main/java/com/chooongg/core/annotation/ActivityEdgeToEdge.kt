package com.chooongg.core.annotation

import androidx.annotation.IntDef
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityEdgeToEdge(
    val isEdgeToEdge: Boolean = true,
    @InsetsSide val fitsSide: Int = LEFT or BOTTOM or RIGHT
) {
    companion object {
        const val LEFT = 1 shl 0
        const val TOP = 1 shl 1
        const val RIGHT = 1 shl 2
        const val BOTTOM = 1 shl 3
    }

    @IntDef(flag = true, value = [TOP, BOTTOM, LEFT, RIGHT])
    annotation class InsetsSide
}
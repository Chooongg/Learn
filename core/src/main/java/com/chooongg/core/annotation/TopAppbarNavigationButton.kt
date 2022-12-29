package com.chooongg.core.annotation

import androidx.annotation.IntDef
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TopAppbarNavigationButton(
    val isShow: Boolean = true,
    @IconType val iconType: Int = ICON_TYPE_BACK
) {

    companion object {
        const val ICON_TYPE_BACK = 0
        const val ICON_TYPE_CLOSE = 1
        const val ICON_TYPE_CUSTOM = 2
    }

    @IntDef(ICON_TYPE_BACK, ICON_TYPE_CLOSE, ICON_TYPE_CUSTOM)
    annotation class IconType
}

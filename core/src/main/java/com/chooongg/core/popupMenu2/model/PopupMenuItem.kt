package com.chooongg.core.popupMenu2.model

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class PopupMenuItem : BasePopupMenuItem() {
    var label: CharSequence? = null

    @StringRes
    var labelRes: Int? = null

    @ColorInt
    var labelColor: Int? = null

    @DrawableRes
    var icon: Int? = null
    var iconDrawable: Drawable? = null
    var iconTint: ColorStateList? = null
    var hasNestedItems: Boolean = false
}
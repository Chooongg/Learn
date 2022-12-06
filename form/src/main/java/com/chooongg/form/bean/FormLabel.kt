package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.FormManager

class FormLabel(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_LABEL, name, field) {

    /**
     * 图标资源
     */
    @DrawableRes
    var icon: Int? = null

    /**
     * 图标着色
     */
    var iconTint: (Context.() -> ColorStateList)? = null

    /**
     * 图标尺寸
     */
    @androidx.annotation.IntRange(from = 0)
    var iconSize: Int? = null

}
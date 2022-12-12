package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.FormManager

class FormMenu(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_MENU, name, field) {

    /**
     * 名称文本颜色
     */
    var nameTextColor: (Context.() -> ColorStateList)? = null

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

    /**
     * 显示更多图标
     */
    var showMoreIcon: Boolean = true
}
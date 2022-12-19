package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.basic.ext.attrColor
import com.chooongg.form.FormManager
import com.chooongg.form.R

class FormMenu(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_MENU, name, field) {

    override var menuIcon: Int? = R.drawable.form_ic_more

    override var menuIconTint: (Context.() -> ColorStateList)? = {
        ColorStateList.valueOf(attrColor(com.google.android.material.R.attr.colorSecondary))
    }

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
     * 气泡文本
     */
    var bubbleText: CharSequence? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormMenu) return false
        if (!super.equals(other)) return false

        if (icon != other.icon) return false
        if (iconSize != other.iconSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (icon ?: 0)
        result = 31 * result + (iconSize ?: 0)
        return result
    }
}
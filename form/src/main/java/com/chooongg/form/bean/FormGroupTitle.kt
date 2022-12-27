package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormGroupTitleMode

class FormGroupTitle internal constructor(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_GROUP_NAME, name, field) {

    /**
     * 名字颜色
     */
    var nameColor: (Context.() -> ColorStateList)? = null

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
     * 模式
     */
    var mode = FormGroupTitleMode.NORMAL

    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormGroupTitle) return false
        if (!super.equals(other)) return false

        if (icon != other.icon) return false
        if (iconSize != other.iconSize) return false
        if (mode != other.mode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (icon ?: 0)
        result = 31 * result + (iconSize ?: 0)
        result = 31 * result + mode.hashCode()
        return result
    }
}
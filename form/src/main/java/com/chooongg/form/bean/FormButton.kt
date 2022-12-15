package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormButtonGravity
import com.chooongg.form.enum.FormEnableMode
import com.google.android.material.button.MaterialButton

class FormButton(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_BUTTON, name, field) {

    override var enableMode: FormEnableMode = FormEnableMode.ALWAYS

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
     * 图标位置
     */
    @MaterialButton.IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_START

    /**
     * 图标边距
     */
    @androidx.annotation.IntRange(from = 0)
    var iconPadding: Int = 0

    /**
     * 按钮重力
     */
    var gravity: FormButtonGravity = FormButtonGravity.CENTER

    /**
     * 按钮宽度
     */
    @androidx.annotation.IntRange(from = -2)
    var width: Int = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT

    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormButton) return false
        if (!super.equals(other)) return false

        if (icon != other.icon) return false
        if (iconSize != other.iconSize) return false
        if (iconGravity != other.iconGravity) return false
        if (iconPadding != other.iconPadding) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (icon ?: 0)
        result = 31 * result + (iconSize ?: 0)
        result = 31 * result + iconGravity
        result = 31 * result + iconPadding
        return result
    }
}
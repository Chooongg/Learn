package com.chooongg.form.bean

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.DrawableRes
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormVisibilityMode
import com.google.android.material.button.MaterialButton

class FormButton(name: CharSequence) : BaseForm(FormManager.TYPE_BUTTON, name) {

    @DrawableRes
    var icon: Int? = null

    var iconTint: ColorStateList? = null

    @androidx.annotation.IntRange(from = 0)
    var iconSize: Int = 0

    @MaterialButton.IconGravity
    var iconGravity: Int = MaterialButton.ICON_GRAVITY_START

    var iconPadding: Int = 0

    @Deprecated("无效")
    override var menuIconTint: ColorStateList?
        get() = super.menuIconTint
        set(value) {}

    @Deprecated("无效")
    override var menuVisibilityMode: FormVisibilityMode
        get() = super.menuVisibilityMode
        set(value) {}

    @Deprecated("无效")
    override fun menuIcon(icon: Int, block: ((View) -> Unit)?) {
    }

    @Deprecated("无效")
    override fun clearMenuIcon() {
    }

    @Deprecated("无效", ReplaceWith("false"))
    override fun isRealMenuVisible(manager: FormManager): Boolean {
        return false
    }
}
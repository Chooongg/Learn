package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import com.chooongg.form.FormManager

class FormTip(name: CharSequence) : BaseForm(FormManager.TYPE_TIP, name, null) {

    override var ignoreNameEms: Boolean = true

    /**
     * 图标着色
     */
    var textColor: (Context.() -> ColorStateList)? = null
}
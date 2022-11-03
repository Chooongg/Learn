package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormText(
    name: CharSequence,
    block: (FormText.() -> Unit)? = null
) : BaseForm(FormManager.TYPE_TEXT, name) {
    init {
        block?.invoke(this)
    }
}
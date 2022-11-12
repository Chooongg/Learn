package com.chooongg.form.bean

import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager

class FormText(name: CharSequence) : BaseForm(FormManager.TYPE_TEXT, name) {

    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }
}
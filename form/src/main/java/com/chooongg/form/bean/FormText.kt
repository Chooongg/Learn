package com.chooongg.form.bean

import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager

class FormText(name: CharSequence, field: String?) : BaseForm(FormManager.TYPE_TEXT, name, field) {
    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }
}
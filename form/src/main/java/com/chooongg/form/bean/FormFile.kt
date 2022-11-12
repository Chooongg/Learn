package com.chooongg.form.bean

import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager

class FormFile(name: CharSequence) : BaseForm(FormManager.TYPE_FILE, name) {

    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }

}
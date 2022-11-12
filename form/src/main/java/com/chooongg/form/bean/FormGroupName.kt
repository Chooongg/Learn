package com.chooongg.form.bean

import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager

class FormGroupName(name: CharSequence) : BaseForm(FormManager.TYPE_GROUP_NAME, name) {

    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }

}
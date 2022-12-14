package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormSwitch(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_SWITCH, name, field) {
    var checkedParams: String = "1"
    var uncheckedParams: String = "0"

    override fun configData() {
        if (content == null) {
            content = uncheckedParams
        }
    }
}
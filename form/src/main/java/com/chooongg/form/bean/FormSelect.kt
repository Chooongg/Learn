package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormSelect(name: CharSequence, field: String?) :
    BaseOptionForm(FormManager.TYPE_SELECT, name, field) {

    override var seeType: Int = FormManager.TYPE_TEXT

    var selectorTitle: CharSequence? = null
}
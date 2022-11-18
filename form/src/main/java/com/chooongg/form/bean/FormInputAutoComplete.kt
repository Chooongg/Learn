package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormInputAutoComplete(name: CharSequence, field: String?) :
    BaseOptionForm(FormManager.TYPE_INPUT_AUTO_COMPLETE, name, field) {

    override var seeType: Int = FormManager.TYPE_TEXT

}
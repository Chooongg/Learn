package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormInputAutoComplete(name: CharSequence) : BaseOptionForm(FormManager.TYPE_INPUT_AUTO_COMPLETE, name) {

    override var seeType:Int = FormManager.TYPE_TEXT

}
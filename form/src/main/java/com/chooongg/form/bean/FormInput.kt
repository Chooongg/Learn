package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormInput(name: CharSequence) : BaseForm(FormManager.TYPE_INPUT, name) {

    override var seeType:Int = FormManager.TYPE_TEXT

}
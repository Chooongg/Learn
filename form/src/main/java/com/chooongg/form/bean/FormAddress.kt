package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormAddress(name: CharSequence) : BaseForm(FormManager.TYPE_ADDRESS, name) {

    override var seeType:Int = FormManager.TYPE_TEXT

}
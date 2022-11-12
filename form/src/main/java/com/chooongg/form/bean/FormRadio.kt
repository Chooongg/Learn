package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormRadio(name: CharSequence) : BaseOptionForm(FormManager.TYPE_RADIO, name) {

    override var seeType:Int = FormManager.TYPE_TEXT

}
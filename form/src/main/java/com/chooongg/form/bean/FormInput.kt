package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormInput(name: CharSequence) : BaseForm(FormManager.TYPE_INPUT, name) {

    override var seeType: Int = FormManager.TYPE_TEXT

    /**
     * 前缀文本
     */
    var prefixText: CharSequence? = null

    /**
     * 后缀文本
     */
    var suffixText: CharSequence? = null

}
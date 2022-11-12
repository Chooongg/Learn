package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormOptionsLoaderMode

class FormSelect(name: CharSequence) : BaseOptionForm(FormManager.TYPE_SELECT, name) {

    override var seeType: Int = FormManager.TYPE_TEXT

    /**
     * 选项加载模式
     */
    var optionsLoaderMode: FormOptionsLoaderMode = FormOptionsLoaderMode.BINDING

}
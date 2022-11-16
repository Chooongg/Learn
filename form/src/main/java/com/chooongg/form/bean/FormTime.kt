package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormTimeMode

class FormTime(name: CharSequence, val mode: FormTimeMode) : BaseForm(FormManager.TYPE_TIME, name) {

}
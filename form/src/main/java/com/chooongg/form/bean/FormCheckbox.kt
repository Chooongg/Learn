package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormCheckbox(name: CharSequence, field: String?) :
    BaseMultipleOptionForm(FormManager.TYPE_CHECKBOX, name, field) {
}
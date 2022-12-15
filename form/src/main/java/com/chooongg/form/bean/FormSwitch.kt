package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormSwitch(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_SWITCH, name, field) {
    var checkedParams: String = "1"
    var uncheckedParams: String = "0"

    override fun configData() {
        if (content == null) {
            content = uncheckedParams
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormSwitch) return false
        if (!super.equals(other)) return false

        if (checkedParams != other.checkedParams) return false
        if (uncheckedParams != other.uncheckedParams) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + checkedParams.hashCode()
        result = 31 * result + uncheckedParams.hashCode()
        return result
    }
}
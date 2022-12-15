package com.chooongg.form.bean

import android.content.Context
import com.chooongg.form.FormManager

class FormSelect(name: CharSequence, field: String?) :
    BaseOptionForm(FormManager.TYPE_SELECT, name, field) {

    override var seeType: Int = FormManager.TYPE_TEXT

    var selectorTitle: CharSequence? = null

    override fun transformContent(context: Context): CharSequence? {
        return options?.find { content == it.getKey() }?.getValue()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormSelect) return false
        if (!super.equals(other)) return false

        if (selectorTitle != other.selectorTitle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (selectorTitle?.hashCode() ?: 0)
        return result
    }
}
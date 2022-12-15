package com.chooongg.form.bean

import android.content.Context
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.style
import com.chooongg.basic.utils.SpanUtils
import com.chooongg.form.FormManager

class FormInputAutoComplete(name: CharSequence, field: String?) :
    BaseOptionForm(FormManager.TYPE_INPUT_AUTO_COMPLETE, name, field) {

    override var seeType: Int = FormManager.TYPE_TEXT

    /**
     * 前缀文本
     */
    var prefixText: CharSequence? = null

    /**
     * 后缀文本
     */
    var suffixText: CharSequence? = null

    override fun transformContent(context: Context): CharSequence? {
        if (content.isNullOrEmpty()) return null
        val spans = ArrayList<SpanUtils>()
        if (prefixText != null) {
            spans.add(prefixText!!.style {
                setForegroundColor(context.attrColor(com.google.android.material.R.attr.colorOutline))
            })
        }
        spans.add(SpanUtils(content!!))
        if (suffixText != null) {
            spans.add(suffixText!!.style {
                setForegroundColor(context.attrColor(com.google.android.material.R.attr.colorOutline))
            })
        }
        val span = spans[0]
        for (i in 1 until spans.size) {
            span + spans[i]
        }
        return span.toSpannableString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormInputAutoComplete) return false
        if (!super.equals(other)) return false

        if (prefixText != other.prefixText) return false
        if (suffixText != other.suffixText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (prefixText?.hashCode() ?: 0)
        result = 31 * result + (suffixText?.hashCode() ?: 0)
        return result
    }
}
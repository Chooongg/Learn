package com.chooongg.form.bean

import android.content.Context
import android.text.InputType
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.style
import com.chooongg.basic.utils.SpanUtils
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormVisibilityMode

class FormInput(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_INPUT, name, field) {

    override var seeType: Int = FormManager.TYPE_TEXT

    /**
     * 前缀文本
     */
    var prefixText: CharSequence? = null

    /**
     * 后缀文本
     */
    var suffixText: CharSequence? = null

    /**
     * 输入类型
     */
    var inputType: Int = InputType.TYPE_CLASS_TEXT

    /**
     * 强制改变的输入类型
     */
    var rawInputType: Int = InputType.TYPE_NULL

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

    override fun isRealMenuVisible(manager: BaseFormManager): Boolean {
        if (suffixText != null) return false
        if (menuIcon == null) return false
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_SEE -> !manager.isEditable
            FormVisibilityMode.ONLY_EDIT -> manager.isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormInput) return false
        if (!super.equals(other)) return false

        if (prefixText != other.prefixText) return false
        if (suffixText != other.suffixText) return false
        if (inputType != other.inputType) return false
        if (rawInputType != other.rawInputType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (prefixText?.hashCode() ?: 0)
        result = 31 * result + (suffixText?.hashCode() ?: 0)
        result = 31 * result + inputType
        result = 31 * result + rawInputType
        return result
    }
}
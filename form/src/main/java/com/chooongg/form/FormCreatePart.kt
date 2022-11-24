package com.chooongg.form

import com.chooongg.form.bean.*

open class FormCreatePart internal constructor() {
    internal val createdFormGroupList = ArrayList<BaseForm>()

    fun addText(name: CharSequence, field: String?, block: (FormText.() -> Unit)? = null) {
        createdFormGroupList.add(FormText(name, field).apply {
            block?.invoke(this)
        })
    }

    fun addButton(name: CharSequence, field: String?, block: (FormButton.() -> Unit)? = null) {
        createdFormGroupList.add(FormButton(name, field).apply {
            block?.invoke(this)
        })
    }

    fun addDivider(block: (FormDivider.() -> Unit)? = null) {
        createdFormGroupList.add(FormDivider().apply {
            block?.invoke(this)
        })
    }

    fun addInput(name: CharSequence, field: String?, block: (FormInput.() -> Unit)? = null){
        createdFormGroupList.add(FormInput(name, field).apply {
            block?.invoke(this)
        })
    }
}
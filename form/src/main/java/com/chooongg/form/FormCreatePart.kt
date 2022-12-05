package com.chooongg.form

import com.chooongg.form.bean.*

open class FormCreatePart internal constructor() {

    internal val createdFormGroupList = ArrayList<BaseForm>()


    fun addText(name: CharSequence, field: String?, block: (FormText.() -> Unit)? = null) {
        createdFormGroupList.add(FormText(name, field).apply { block?.invoke(this) })
    }


    fun addAddress(name: CharSequence, field: String?, block: (FormAddress.() -> Unit)? = null) {
        createdFormGroupList.add(FormAddress(name, field).apply { block?.invoke(this) })
    }

    fun addAddressMust(
        name: CharSequence,
        field: String?,
        block: (FormAddress.() -> Unit)? = null
    ) {
        addAddress(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addButton(name: CharSequence, field: String?, block: (FormButton.() -> Unit)? = null) {
        createdFormGroupList.add(FormButton(name, field).apply { block?.invoke(this) })
    }

    fun addButtonMust(name: CharSequence, field: String?, block: (FormButton.() -> Unit)? = null) {
        addButton(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addDivider(block: (FormDivider.() -> Unit)? = null) {
        createdFormGroupList.add(FormDivider().apply { block?.invoke(this) })
    }


    fun addInput(name: CharSequence, field: String?, block: (FormInput.() -> Unit)? = null) {
        createdFormGroupList.add(FormInput(name, field).apply { block?.invoke(this) })
    }

    fun addInputMust(name: CharSequence, field: String?, block: (FormInput.() -> Unit)? = null) {
        addInput(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addInputAutoComplete(
        name: CharSequence,
        field: String?,
        block: (FormInputAutoComplete.() -> Unit)? = null
    ) {
        createdFormGroupList.add(FormInputAutoComplete(name, field).apply { block?.invoke(this) })
    }

    fun addInputAutoCompleteMust(
        name: CharSequence,
        field: String?,
        block: (FormInputAutoComplete.() -> Unit)? = null
    ) {
        addInputAutoComplete(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }
}
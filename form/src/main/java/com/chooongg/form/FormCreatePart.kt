package com.chooongg.form

import com.chooongg.form.bean.*

open class FormCreatePart internal constructor() {

    internal val createdFormGroupList = ArrayList<BaseForm>()

    fun add(item: BaseForm) {
        createdFormGroupList.add(item)
    }


    fun addText(name: CharSequence, field: String?, block: (FormText.() -> Unit)? = null) {
        add(FormText(name, field).apply { block?.invoke(this) })
    }


    fun addAddress(name: CharSequence, field: String?, block: (FormAddress.() -> Unit)? = null) {
        add(FormAddress(name, field).apply { block?.invoke(this) })
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
        add(FormButton(name, field).apply { block?.invoke(this) })
    }

    fun addButtonMust(name: CharSequence, field: String?, block: (FormButton.() -> Unit)? = null) {
        addButton(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addCheckbox(name: CharSequence, field: String?, block: (FormCheckbox.() -> Unit)? = null) {
        add(FormCheckbox(name, field).apply { block?.invoke(this) })
    }

    fun addCheckboxMust(
        name: CharSequence,
        field: String?,
        block: (FormCheckbox.() -> Unit)? = null
    ) {
        addCheckbox(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addDivider(block: (FormDivider.() -> Unit)? = null) {
        add(FormDivider().apply { block?.invoke(this) })
    }


    fun addInput(name: CharSequence, field: String?, block: (FormInput.() -> Unit)? = null) {
        add(FormInput(name, field).apply { block?.invoke(this) })
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
        add(FormInputAutoComplete(name, field).apply { block?.invoke(this) })
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


    fun addLabel(name: CharSequence, field: String? = null, block: (FormLabel.() -> Unit)? = null) {
        add(FormLabel(name, field).apply { block?.invoke(this) })
    }

    fun addLabelMust(
        name: CharSequence,
        field: String? = null,
        block: (FormLabel.() -> Unit)? = null
    ) {
        addLabel(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addMenu(name: CharSequence, field: String?, block: (FormMenu.() -> Unit)? = null) {
        add(FormMenu(name, field).apply { block?.invoke(this) })
    }

    fun addMenuMust(name: CharSequence, field: String?, block: (FormMenu.() -> Unit)? = null) {
        addMenu(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }


    fun addRadio(name: CharSequence, field: String?, block: (FormRadio.() -> Unit)? = null) {
        add(FormRadio(name, field).apply { block?.invoke(this) })
    }

    fun addRadioMust(name: CharSequence, field: String?, block: (FormRadio.() -> Unit)? = null) {
        addRadio(name, field) {
            block?.invoke(this)
            isMust = true
        }
    }
}
package com.chooongg.form.provider

import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormText
import com.chooongg.form.style.FormBoundary

internal class FormTextProvider(manager: FormManager) : BaseFormProvider<FormText>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_TEXT
    override val layoutId: Int get() = R.layout.item_form_text
    override fun onBindViewHolder(holder: FormViewHolder, boundary: FormBoundary, item: FormText) {

    }
}
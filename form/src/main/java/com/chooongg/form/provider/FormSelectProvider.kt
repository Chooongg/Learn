package com.chooongg.form.provider

import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormSelect
import com.google.android.material.textview.MaterialTextView

class FormSelectProvider(manager: FormManager) : BaseFormProvider<FormSelect>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_SELECT
    override val layoutId: Int get() = R.layout.form_item_text
    override fun onBindViewHolder(holder: FormViewHolder, item: FormSelect) {
        with(holder.getView<MaterialTextView>(R.id.form_tv_content)) {
            setTextIsSelectable(true)
            isEnabled = item.isEnabled
            text = item.transformContent(context)
            hint = item.hint ?: context.getString(R.string.form_none)
        }
    }
}
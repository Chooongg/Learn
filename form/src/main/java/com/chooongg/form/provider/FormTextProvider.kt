package com.chooongg.form.provider

import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.google.android.material.textview.MaterialTextView

internal class FormTextProvider(manager: BaseFormManager) : BaseFormProvider<BaseForm>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_TEXT
    override val layoutId: Int get() = R.layout.form_item_text
    override fun onBindViewHolder(holder: FormViewHolder, item: BaseForm) {
        with(holder.getView<MaterialTextView>(R.id.form_tv_content)) {
            setTextIsSelectable(true)
            isEnabled = item.isRealEnable(manager)
            text = item.transformContent(context)
            hint = item.hint ?: context.getString(R.string.form_none)
        }
    }
}
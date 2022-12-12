package com.chooongg.form.provider

import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormTip
import com.google.android.material.textview.MaterialTextView

class FormTipProvider(manager: BaseFormManager) : BaseFormProvider<FormTip>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_TIP
    override val layoutId: Int get() = R.layout.form_item_tip
    override fun onBindViewHolder(holder: FormViewHolder, item: FormTip) {
        with(holder.getView<MaterialTextView>(R.id.form_tv_name)) {
            setTextIsSelectable(true)
            hint = item.hint
        }
    }
}
package com.chooongg.form.provider

import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormRate
import com.google.android.material.textview.MaterialTextView

class FormRateProvider(manager: FormManager) : BaseFormProvider<FormRate>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_RATE
    override val layoutId: Int get() = R.layout.form_item_text
    override fun onBindViewHolder(holder: FormViewHolder, item: FormRate) {
    }
}
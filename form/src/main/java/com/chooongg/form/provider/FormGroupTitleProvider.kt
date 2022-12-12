package com.chooongg.form.provider

import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.FormGroupTitle

class FormGroupTitleProvider(manager: BaseFormManager) : BaseFormProvider<FormGroupTitle>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_GROUP_NAME
    override val layoutId: Int get() = style!!.getGroupTitleLayoutId()
    override fun onBindViewHolder(holder: FormViewHolder, item: FormGroupTitle) {
        style!!.onBindGroupTitleHolder(manager, groupAdapter, holder, item)
    }
}
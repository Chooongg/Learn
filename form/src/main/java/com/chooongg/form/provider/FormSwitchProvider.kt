package com.chooongg.form.provider

import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormSwitch
import com.google.android.material.materialswitch.MaterialSwitch

class FormSwitchProvider(manager: BaseFormManager) : BaseFormProvider<FormSwitch>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_SWITCH
    override val layoutId: Int get() = R.layout.form_item_switch
    override fun onBindViewHolder(holder: FormViewHolder, item: FormSwitch) {
        with(holder.getView<MaterialSwitch>(R.id.form_switch)) {
            isEnabled = item.isRealEnable(manager)
            setOnCheckedChangeListener(null)
            isChecked = item.content == item.checkedParams
            setOnCheckedChangeListener { _, isChecked ->
                item.content = if (isChecked) item.checkedParams else item.uncheckedParams
                groupAdapter?.onFormContentChanged(manager, item, holder.absoluteAdapterPosition)
            }
        }
    }
}
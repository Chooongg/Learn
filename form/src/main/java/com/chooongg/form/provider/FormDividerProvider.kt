package com.chooongg.form.provider

import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormDivider
import com.chooongg.form.style.FormBoundary
import com.google.android.material.divider.MaterialDivider

class FormDividerProvider(manager: FormManager) : BaseFormProvider<FormDivider>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_DIVIDER
    override val layoutId: Int get() = R.layout.form_item_divider

    override fun onBindViewHolder(
        holder: FormViewHolder,
        boundary: FormBoundary,
        item: FormDivider
    ) {
        with(holder.getView<MaterialDivider>(R.id.form_divider)) {
            dividerColor = item.color
                ?: attrColor(com.google.android.material.R.attr.colorOutlineVariant)
            dividerThickness = item.thickness
                ?: resDimensionPixelSize(com.chooongg.basic.R.dimen.divider)
            dividerInsetStart = item.insetStart
                ?: resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium)
            dividerInsetEnd = item.insetEnd
                ?: resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium)
        }
    }
}
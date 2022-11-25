package com.chooongg.form.provider

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.google.android.material.textview.MaterialTextView

internal class FormTextProvider(manager: FormManager) : BaseFormProvider<BaseForm>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_TEXT
    override val layoutId: Int get() = R.layout.form_item_text
    override fun onBindViewHolder(holder: FormViewHolder, item: BaseForm) {
        with(holder.getView<MaterialTextView>(R.id.form_tv_content)) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                goneEndMargin = manager.itemHorizontalSize - paddingEnd
                marginEnd = 0
            }
            setTextIsSelectable(true)
            isEnabled = item.isEnabled
            text = item.transformContent()
            hint = item.hint ?: context.getString(R.string.form_none)
        }
    }
}
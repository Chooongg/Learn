package com.chooongg.form.provider

import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.gone
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.visible
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormLabel
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max

class FormLabelProvider(manager: FormManager) : BaseFormProvider<FormLabel>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_LABEL
    override val layoutId: Int get() = R.layout.form_item_label
    override fun onBindViewHolder(holder: FormViewHolder, item: FormLabel) {
        with(holder.getView<AppCompatImageView>(R.id.form_iv_icon)) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                marginStart = max(0, manager.itemHorizontalSize - paddingStart)
                width = item.iconSize ?: resDimensionPixelSize(R.dimen.formItemIconSize)
            }
            if (item.icon != null) {
                imageTintList = item.iconTint?.invoke(context)
                setImageResource(item.icon!!)
                visible()
            } else gone()
        }
        with(holder.getView<MaterialTextView>(R.id.form_tv_name)) {
            hint = item.hint
        }
    }
}
package com.chooongg.form.provider

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormButton
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormButtonGravity
import com.google.android.material.button.MaterialButton

class FormButtonProvider(manager: BaseFormManager) : BaseFormProvider<FormButton>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_BUTTON
    override val layoutId: Int get() = R.layout.form_item_button
    override fun onBindViewHolder(holder: FormViewHolder, item: FormButton) {
        with(holder.getView<MaterialButton>(R.id.form_button)) {
            isEnabled = item.isRealEnable(manager)
            text = item.name
            if (item.icon != null) {
                iconTint = item.iconTint?.invoke(context)
                setIconResource(item.icon!!)
            } else icon = null
            iconSize =
                item.iconSize ?: context.resDimensionPixelSize(com.chooongg.basic.R.dimen.d48)
            iconGravity = item.iconGravity
            iconPadding = item.iconPadding
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = item.width
                topMargin = if (item.adapterTopBoundary == FormBoundaryType.NONE) 0
                else resDimensionPixelSize(R.dimen.formPartVertical)
                bottomMargin = if (item.adapterBottomBoundary == FormBoundaryType.NONE) 0
                else resDimensionPixelSize(R.dimen.formPartVertical)
                when (item.gravity) {
                    FormButtonGravity.START -> {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.UNSET
                    }
                    FormButtonGravity.CENTER -> {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                    FormButtonGravity.END -> {
                        startToStart = ConstraintLayout.LayoutParams.UNSET
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }
                }
            }
            doOnClick {
                groupAdapter?.clearFocus()
                groupAdapter?.onFormClick(manager, item, it, holder.absoluteAdapterPosition)
            }
        }
    }

    override fun configItemClick(holder: FormViewHolder, item: FormButton) = Unit
}
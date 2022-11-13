package com.chooongg.form.provider

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormButton
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.style.FormBoundary
import com.google.android.material.button.MaterialButton

class FormButtonProvider(manager: FormManager) : BaseFormProvider<FormButton>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_BUTTON
    override val layoutId: Int get() = R.layout.form_item_button
    override fun onBindViewHolder(
        holder: FormViewHolder,
        boundary: FormBoundary,
        item: FormButton
    ) {
        with(holder.getView<MaterialButton>(R.id.form_button)) {
            text = item.name
            if (item.icon != null) {
                setIconResource(item.icon!!)
            } else icon = null
            iconTint = item.iconTint
            iconSize = item.iconSize
            iconGravity = item.iconGravity
            iconPadding = item.iconPadding
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                topMargin = if (boundary.top != FormBoundaryType.NONE) {
                    resDimensionPixelSize(R.dimen.formContentMedium)
                } else 0
                bottomMargin = if (boundary.bottom != FormBoundaryType.NONE) {
                    resDimensionPixelSize(R.dimen.formContentMedium)
                } else 0
            }
        }
    }
}
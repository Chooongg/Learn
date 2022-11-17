package com.chooongg.form.provider

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.multipleValid
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormButton
import com.chooongg.form.enum.FormBoundaryType
import com.google.android.material.button.MaterialButton

class FormButtonProvider(manager: FormManager) : BaseFormProvider<FormButton>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_BUTTON
    override val layoutId: Int get() = R.layout.form_item_button
    override fun onBindViewHolder(holder: FormViewHolder, item: FormButton) {
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
                topMargin = if (item.adapterTopBoundary == FormBoundaryType.NONE) 0
                else resDimensionPixelSize(R.dimen.formContentMedium)
                bottomMargin = if (item.adapterBottomBoundary == FormBoundaryType.NONE) 0
                else resDimensionPixelSize(R.dimen.formContentMedium)
            }
//            if (item.itemClickBlock != null) doOnClick(item.itemClickBlock!!)
//            else setOnClickListener(null)
            setOnClickListener {
                recyclerView?.clearFocus()
                if (multipleValid()) {
                    item.itemClickBlock?.invoke(it)
                }
            }
        }
    }
}
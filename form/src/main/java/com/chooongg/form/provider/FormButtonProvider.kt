package com.chooongg.form.provider

import android.animation.AnimatorInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.*
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormButton
import com.chooongg.form.enum.FormButtonGravity
import com.chooongg.form.enum.FormButtonStyle
import com.google.android.material.button.MaterialButton
import com.google.android.material.theme.overlay.MaterialThemeOverlay

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
            iconSize = item.iconSize ?: context.resDimensionPixelSize(R.dimen.formItemIconSize)
            iconGravity = item.iconGravity
            iconPadding = item.iconPadding
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                width = item.width
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
            val style = when (item.style) {
                FormButtonStyle.DEFAULT -> attrResourcesId(
                    com.google.android.material.R.attr.materialButtonStyle, 0
                )
                FormButtonStyle.TEXT ->
                    com.google.android.material.R.style.Widget_Material3_Button_TextButton
                FormButtonStyle.TONAL ->
                    com.google.android.material.R.style.Widget_Material3_Button_TonalButton
                FormButtonStyle.OUTLINED -> attrResourcesId(
                    com.google.android.material.R.attr.materialButtonOutlinedStyle, 0
                )
                FormButtonStyle.UN_ELEVATED ->
                    com.google.android.material.R.style.Widget_Material3_Button_UnelevatedButton
            }
            val wrap = MaterialThemeOverlay.wrap(context, null, 0, style)
            setTextColor(wrap.resChildColorStateList(style, android.R.attr.textColor))
            iconTint = wrap.resChildColorStateList(
                style, androidx.appcompat.R.attr.iconTint
            )
            backgroundTintList = wrap.resChildColorStateList(
                style, androidx.appcompat.R.attr.backgroundTint
            )
            strokeColor = wrap.resChildColorStateList(
                style, com.google.android.material.R.attr.strokeColor
            )
            strokeWidth = wrap.resChildDimensionPixelSize(
                style, com.google.android.material.R.attr.strokeWidth, 0
            )
            rippleColor = wrap.resChildColorStateList(
                style, com.google.android.material.R.attr.rippleColor
            )
            elevation = wrap.resChildDimension(
                style, com.google.android.material.R.attr.elevation, 0f
            )
            val stateListId = wrap.resChildResourcesId(style, android.R.attr.stateListAnimator, 0)
            stateListAnimator = if (stateListId != 0) {
                AnimatorInflater.loadStateListAnimator(wrap, stateListId);
            } else null
            doOnClick {
                groupAdapter?.clearFocus()
                groupAdapter?.onFormClick(manager, item, it, holder.absoluteAdapterPosition)
            }
        }
    }

    override fun configItemClick(holder: FormViewHolder, item: FormButton) = Unit
}
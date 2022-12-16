package com.chooongg.form.provider

import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.*
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormMenu
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max

class FormMenuProvider(manager: BaseFormManager) : BaseFormProvider<FormMenu>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_MENU
    override val layoutId: Int get() = R.layout.form_item_menu
    override fun onBindViewHolder(holder: FormViewHolder, item: FormMenu) {
        holder.itemView.isEnabled = item.isRealEnable(manager)
        when (val parent = holder.itemView) {
            is MaterialCardView -> {
                parent.rippleColor = parent.attrChildColorStateList(
                    com.google.android.material.R.attr.materialCardViewStyle,
                    com.google.android.material.R.attr.rippleColor
                )
            }
            else -> parent.background =
                parent.attrDrawable(androidx.appcompat.R.attr.selectableItemBackground)
        }
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
            text = item.name
            if (item.nameTextColor != null) {
                setTextColor(item.nameTextColor!!.invoke(context))
            } else setTextColorAttr(com.google.android.material.R.attr.colorOnSurface)
        }
        with(holder.getView<MaterialButton>(R.id.form_btn_menu)) {
            isEnabled = false
        }
    }
}
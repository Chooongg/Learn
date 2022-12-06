package com.chooongg.form.provider

import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.*
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormMenu
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max

class FormMenuProvider(manager: FormManager) : BaseFormProvider<FormMenu>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_MENU
    override val layoutId: Int get() = R.layout.form_item_menu
    override fun onBindViewHolder(holder: FormViewHolder, item: FormMenu) {
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
                isEnabled = item.isEnabled
                imageTintList = item.iconTint?.invoke(context)
                setImageResource(item.icon!!)
                visible()
            } else gone()
        }
        with(holder.getView<MaterialTextView>(R.id.form_tv_name)) {
            isEnabled = item.isEnabled
            text = item.name
        }
        with(holder.getView<AppCompatImageView>(R.id.form_iv_menu)) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                marginEnd = if (item.showMoreIcon) 0
                else max(0, manager.itemHorizontalSize - paddingEnd)
            }
            if (item.isRealMenuVisible(manager) && item.menuIcon != null) {
                imageTintList = item.menuIconTint?.invoke(context)
                setImageResource(item.menuIcon!!)
                visible()
            } else gone()
            doOnClick {
                groupAdapter?.clearFocus()
                groupAdapter?.onFormMenuClick(manager, item, this, holder.absoluteAdapterPosition)
            }
        }
        with(holder.getView<AppCompatImageView>(R.id.form_iv_more)) {
            if (item.showMoreIcon) {
                updateLayoutParams<ConstraintLayout.LayoutParams> {
                    marginEnd = max(0, manager.itemHorizontalSize - paddingEnd)
                }
                visible()
            } else gone()
        }
    }
}
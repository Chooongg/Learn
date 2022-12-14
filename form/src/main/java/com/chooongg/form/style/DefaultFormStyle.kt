package com.chooongg.form.style

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.gone
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.visible
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormGroupTitleMode
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max

open class DefaultFormStyle : FormStyle() {

    override fun createItemParentView(parent: ViewGroup): ViewGroup? = null

    override fun onBindParentViewHolder(
        manager: BaseFormManager,
        holder: FormViewHolder,
        item: BaseForm
    ) {
        with(holder.itemView) {
            if (this !is ViewGroup) return@with
            if (item is FormGroupTitle) return@with
            if (childCount > 0) {
                getChildAt(0).updatePaddingRelative(
                    top = if (item.adapterTopBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize,
                    bottom = if (item.adapterBottomBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize
                )
            }
        }
    }

    override fun getGroupTitleLayoutId() = R.layout.form_item_group_name_default

    override fun onBindGroupTitleHolder(
        manager: BaseFormManager,
        adapter: FormGroupAdapter?,
        holder: FormViewHolder,
        item: FormGroupTitle
    ) {
        with(holder.getView<AppCompatImageView>(R.id.form_iv_icon)) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                marginStart = max(0, manager.itemHorizontalSize - paddingStart)
                width = item.iconSize ?: resDimensionPixelSize(R.dimen.formItemIconSize)
            }
            if (item.icon != null) {
                isEnabled = item.isRealEnable(manager)
                imageTintList = item.iconTint?.invoke(context)
                setImageResource(item.icon!!)
                visible()
            } else gone()
        }
        with(holder.getView<MaterialTextView>(R.id.form_tv_name)) {
            hint = item.hint
        }
        with(holder.getView<AppCompatImageView>(R.id.form_iv_menu)) {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                marginEnd = max(0, manager.itemHorizontalSize - paddingEnd)
                width = item.iconSize ?: resDimensionPixelSize(R.dimen.formItemMenuIconSize)
            }
            isEnabled = item.isRealMenuEnable(manager)
            if (item.mode == FormGroupTitleMode.ADD) {
                setImageResource(R.drawable.form_ic_add)
                visible()
            } else if (item.mode == FormGroupTitleMode.DELETE) {
                setImageResource(R.drawable.form_ic_remove)
                visible()
            } else if (item.isRealMenuVisible(manager) && item.menuIcon != null) {
                imageTintList = item.menuIconTint?.invoke(context)
                setImageResource(item.menuIcon!!)
                visible()
            } else gone()
            doOnClick {
                adapter?.clearFocus()
                adapter?.onFormMenuClick(manager, item, this, holder.absoluteAdapterPosition)
            }
        }
    }
}
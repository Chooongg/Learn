package com.chooongg.form.style

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.*
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormGroupTitleMode
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max

open class DefaultFormStyle : FormStyle() {

    override fun createItemParentView(parent: ViewGroup): ViewGroup? = null

    override fun onBindParentViewHolder(
        manager: BaseFormManager, holder: FormViewHolder, item: BaseForm
    ) {
        with(holder.itemView) {
            updatePaddingRelative(
                top = if (item.topBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize,
                bottom = if (item.bottomBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize
            )
            updateLayoutParams<RecyclerView.LayoutParams> {
                val recyclerViewWidth = manager._recyclerView?.get()?.width ?: 0
                if (recyclerViewWidth > manager.itemMaxWidth) {
                    marginStart = (recyclerViewWidth - manager.itemMaxWidth) / 2
                    marginEnd = (recyclerViewWidth - manager.itemMaxWidth) / 2
                } else {
                    marginStart = 0
                    marginEnd = 0
                }

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
        with(holder.getView<MaterialButton>(R.id.form_btn_menu)) {
            isEnabled = item.isRealMenuEnable(manager)
            if (item.mode == FormGroupTitleMode.ADD) {
                val tint = ColorStateList.valueOf(attrColor(androidx.appcompat.R.attr.colorPrimary))
                text = "添加"
                setTextColor(tint)
                iconTint = tint
                setIconResource(R.drawable.form_ic_add)
                iconGravity = MaterialButton.ICON_GRAVITY_START
                visible()
            } else if (item.mode == FormGroupTitleMode.DELETE) {
                val tint = ColorStateList.valueOf(attrColor(androidx.appcompat.R.attr.colorError))
                text = "删除"
                setTextColor(tint)
                iconTint = tint
                setIconResource(R.drawable.form_ic_remove)
                iconGravity = MaterialButton.ICON_GRAVITY_START
                visible()
            } else if (item.isRealMenuVisible(manager)) {
                text = item.menuText
                iconTint = item.menuIconTint?.invoke(context)
                if (item.menuIcon != null) setIconResource(item.menuIcon!!) else icon = null
                iconGravity = item.menuIconGravity
                visible()
            } else gone()
            doOnClick {
                adapter?.clearFocus()
                adapter?.onFormMenuClick(manager, item, this, holder.absoluteAdapterPosition)
            }
        }
    }
}
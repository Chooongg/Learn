package com.chooongg.form.style

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.gone
import com.chooongg.basic.ext.visible
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.google.android.material.textview.MaterialTextView

class DefaultFormStyle : FormStyle(0) {

    override fun createItemParentView(parent: ViewGroup): ViewGroup? = null

    override fun onBindParentViewHolder(holder: FormViewHolder, item: BaseForm) = Unit

    override fun getGroupTitleLayoutId() = R.layout.form_item_group_name_default

    override fun onBindGroupTitleHolder(
        manager: FormManager,
        holder: FormViewHolder,
        item: FormGroupTitle
    ) {
        with(holder.getView<AppCompatImageView>(R.id.form_iv_icon)) {
            if (item.icon != null) {
                isEnabled = item.isEnabled
                imageTintList = item.iconTint
                setImageResource(item.icon!!)
                visible()
            } else gone()
        }
        with(holder.getView<MaterialTextView>(R.id.form_tv_name)) {
            isEnabled = item.isEnabled
            text = item.name
        }
        with(holder.getView<AppCompatImageView>(R.id.form_iv_menu)) {
            if (item.isRealMenuVisible(manager) && item.menuIcon != null) {
                isEnabled = item.isEnabled
                imageTintList = item.menuIconTint
                setImageResource(item.menuIcon!!)
//                doOnClick { item.menuIconClickBlock?.invoke(it) }
                visible()
            } else gone()
        }
    }
}
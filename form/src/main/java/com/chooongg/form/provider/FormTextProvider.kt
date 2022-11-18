package com.chooongg.form.provider

import androidx.appcompat.widget.AppCompatImageView
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.gone
import com.chooongg.basic.ext.visible
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormText
import com.google.android.material.textview.MaterialTextView

internal class FormTextProvider(manager: FormManager) : BaseFormProvider<FormText>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_TEXT
    override val layoutId: Int get() = R.layout.form_item_text
    override fun onBindViewHolder(holder: FormViewHolder, item: FormText) {
        with(holder.getView<MaterialTextView>(R.id.form_tv_content)) {
            isEnabled = item.isEnabled
            text = item.transformContent()
            hint = item.hint ?: context.getString(R.string.form_none)
        }
        with(holder.getView<AppCompatImageView>(R.id.form_iv_menu)) {
            if (item.isRealMenuVisible(manager) && item.menuIcon != null) {
                isEnabled = item.isEnabled
                imageTintList = item.menuIconTint
                setImageResource(item.menuIcon!!)
                doOnClick {
                    recyclerView?.clearFocus()
                    adapter?.formEventListener?.onFormMenuClick(
                        manager, item, it, holder.absoluteAdapterPosition
                    )
                }
                visible()
            } else gone()
        }
    }
}
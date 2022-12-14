package com.chooongg.form.style

import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.setText
import com.chooongg.basic.ext.style
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle

abstract class FormStyle {

    /**
     * 创建 Item 外层布局
     * @return 返回外层布局
     */
    abstract fun createItemParentView(parent: ViewGroup): ViewGroup?

    /**
     * 绑定外层布局
     */
    abstract fun onBindParentViewHolder(
        manager: BaseFormManager, holder: FormViewHolder, item: BaseForm
    )

    abstract fun getGroupTitleLayoutId(): Int

    abstract fun onBindGroupTitleHolder(
        manager: BaseFormManager,
        adapter: FormGroupAdapter?,
        holder: FormViewHolder,
        item: FormGroupTitle
    )

    /**
     * 绑定外层布局
     */
    open fun onBindParentViewHolder(
        manager: BaseFormManager, holder: FormViewHolder, item: BaseForm, payloads: MutableList<Any>
    ) = onBindParentViewHolder(manager, holder, item)

    open fun configNameTextView(manager: BaseFormManager, textView: TextView?, item: BaseForm) {
        if (textView == null) return
        textView.isEnabled = item.isRealEnable(manager)
        if (item.ignoreNameEms || item.name.isEmpty()) {
            textView.minWidth = 0
        } else {
            textView.setEms(manager.nameEmsSize)
        }
        if (item.name.isEmpty()) {
            textView.text = null
            return
        }
        if (item.isMust) {
            textView.setText(item.name.style {} + "*".style {
                setForegroundColor(textView.attrColor(androidx.appcompat.R.attr.colorError))
            })
        } else textView.text = item.name
    }
}
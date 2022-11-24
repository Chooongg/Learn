package com.chooongg.form.style

import android.view.ViewGroup
import android.widget.TextView
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.setText
import com.chooongg.basic.ext.style
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle

abstract class FormStyle(
    /**
     * item 类型增量
     * 因为使用 ConcatAdapter 并且关闭了隔离类型
     * 所以每个风格建议以 100 为单位向上增加 ItemViewType
     */
    val typeIncrement: Int
) {

    /**
     * 创建 Item 外层布局
     * @return 返回外层布局
     */
    abstract fun createItemParentView(parent: ViewGroup): ViewGroup?

    /**
     * 绑定外层布局
     */
    abstract fun onBindParentViewHolder(
        manager: FormManager, holder: FormViewHolder, item: BaseForm
    )

    abstract fun getGroupTitleLayoutId(): Int

    abstract fun onBindGroupTitleHolder(
        manager: FormManager, holder: FormViewHolder, item: FormGroupTitle
    )

    /**
     * 绑定外层布局
     */
    open fun onBindParentViewHolder(
        manager: FormManager, holder: FormViewHolder, item: BaseForm, payloads: MutableList<Any>
    ) = onBindParentViewHolder(manager, holder, item)

    open fun configNameTextView(manager: FormManager, textView: TextView?, item: BaseForm) {
        if (textView == null) return
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
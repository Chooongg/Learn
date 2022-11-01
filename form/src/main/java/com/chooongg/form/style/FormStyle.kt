package com.chooongg.form.style

import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.BaseForm

interface FormStyle {

    /**
     * 创建 Item 外层布局
     * @return 返回外层布局
     */
    fun createItemParentView(parent: ViewGroup): ViewGroup

    /**
     * 测试外层布局
     */
    abstract fun onBindParentViewHolder(holder: FormViewHolder, item: BaseForm)

    open fun onBindParentViewHolder(
        holder: FormViewHolder,
        item: BaseForm,
        payloads: MutableList<Any>
    ) = onBindParentViewHolder(holder, item)

}
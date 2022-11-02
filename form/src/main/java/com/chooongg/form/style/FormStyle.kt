package com.chooongg.form.style

import android.content.Context
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder

abstract class FormStyle {

    lateinit var context: Context internal set

    /**
     * 创建 Item 外层布局
     * @return 返回外层布局
     */
    abstract fun createItemParentView(parent: ViewGroup): ViewGroup?

    /**
     * 绑定外层布局
     */
    abstract fun onBindParentViewHolder(holder: FormViewHolder, boundary: FormBoundary)

    /**
     * 绑定外层布局
     */
    open fun onBindParentViewHolder(
        holder: FormViewHolder,
        boundary: FormBoundary,
        payloads: MutableList<Any>
    ) = onBindParentViewHolder(holder, boundary)

}
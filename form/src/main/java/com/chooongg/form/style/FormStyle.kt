package com.chooongg.form.style

import android.content.Context
import android.view.ViewGroup
import com.chooongg.form.FormViewHolder

abstract class FormStyle(
    /**
     * item 类型增量
     * 因为使用 ConcatAdapter 并且关闭了隔离类型
     * 所以每个风格建议以 100 为单位向上增加 ItemViewType
     */
    val typeIncrement: Int
) {

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
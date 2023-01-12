package com.chooongg.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.chooongg.adapter.viewHolder.BaseViewHolder

abstract class BaseAdapter<DATA>(
    @LayoutRes private val resId: Int,
    data: MutableList<DATA>? = null
) : LearnAdapter<DATA, BaseViewHolder>(data) {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder = BaseViewHolder(resId, parent)
}
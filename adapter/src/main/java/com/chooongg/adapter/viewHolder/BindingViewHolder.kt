package com.chooongg.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * 视图绑定 ViewHolder
 */
open class BindingViewHolder<T : ViewBinding>(binding: T) : BaseViewHolder(binding.root)
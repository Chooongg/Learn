package com.chooongg.adapter.viewHolder

import androidx.viewbinding.ViewBinding

/**
 * 视图绑定 ViewHolder
 */
class BindingViewHolder<T : ViewBinding>(val binding: T) : BaseViewHolder(binding.root) {
}
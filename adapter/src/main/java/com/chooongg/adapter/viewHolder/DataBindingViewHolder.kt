package com.chooongg.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class DataBindingViewHolder<T : ViewDataBinding>(val binding: T) : BaseViewHolder(binding.root) {
    constructor(@LayoutRes resId: Int, parent: ViewGroup) : this(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), resId, parent, false)
    )
}
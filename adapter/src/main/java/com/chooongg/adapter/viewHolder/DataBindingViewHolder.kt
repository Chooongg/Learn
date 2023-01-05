package com.chooongg.adapter.viewHolder

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

class DataBindingViewHolder<T : ViewDataBinding> : BaseViewHolder {

    val binding: T

    constructor(@LayoutRes resId:Int){

        super(view)
    }
}
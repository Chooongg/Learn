package com.chooongg.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chooongg.adapter.viewHolder.BindingViewHolder

abstract class BindingAdapter<DATA, T : ViewBinding>(data: MutableList<DATA>? = null) :
    LearnAdapter<DATA, BindingViewHolder<T>>(data) {

    protected open fun getViewBindingTIndex() = 1

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T> =
        BindingViewHolder(AdapterUtils.getBinding(javaClass, context, getViewBindingTIndex()))

    abstract fun onBind(holder: BindingViewHolder<*>, binding: T, position: Int)

    open fun onBind(
        holder: BindingViewHolder<*>,
        binding: T,
        position: Int,
        payloads: MutableList<Any>
    ) = onBind(holder, binding, position)

    final override fun onBind(holder: BindingViewHolder<T>, position: Int) =
        onBind(holder, holder.binding, position)

    final override fun onBind(
        holder: BindingViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) = onBind(holder, holder.binding, position, payloads)


}
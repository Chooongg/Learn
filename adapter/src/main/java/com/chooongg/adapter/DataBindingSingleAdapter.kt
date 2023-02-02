package com.chooongg.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.getBinding
import androidx.databinding.ViewDataBinding
import com.chooongg.adapter.viewHolder.DataBindingViewHolder
import java.lang.reflect.ParameterizedType

abstract class DataBindingSingleAdapter<DATA, T : ViewDataBinding>(data: MutableList<DATA>? = null) :
    LearnAdapter<DATA, DataBindingViewHolder<T>>(data) {

    protected open fun getViewBindingTIndex() = 1

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<T> = DataBindingViewHolder(AdapterUtils.getBinding(javaClass, context, getViewBindingTIndex()))

    abstract fun onBind(holder: DataBindingViewHolder<*>, binding: T, position: Int)

    open fun onBind(
        holder: DataBindingViewHolder<*>,
        binding: T,
        position: Int,
        payloads: MutableList<Any>
    ) = onBind(holder, binding, position)

    final override fun onBind(holder: DataBindingViewHolder<T>, position: Int) =
        onBind(holder, holder.binding, position)

    final override fun onBind(
        holder: DataBindingViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) = onBind(holder, holder.binding, position, payloads)
}
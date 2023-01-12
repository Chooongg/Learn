package com.chooongg.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.chooongg.adapter.viewHolder.DataBindingViewHolder
import java.lang.reflect.ParameterizedType

abstract class DataBindingAdapter<DATA, T : ViewDataBinding>(data: MutableList<DATA>? = null) :
    LearnAdapter<DATA, DataBindingViewHolder<T>>(data) {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<T> = DataBindingViewHolder(getBinding(javaClass, context))

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

    @Suppress("UNCHECKED_CAST")
    private fun <T> getBinding(currentClass: Class<*>, context: Context): T {
        val clazz = currentClass.getTClass()
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, LayoutInflater.from(context)) as T
    }

    private fun Class<*>.getTClass(): Class<*> {
        var genericSuperclass = genericSuperclass
        var superclass = superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                return genericSuperclass.actualTypeArguments[1] as Class<*>
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        throw NullPointerException("没有找到泛型T")
    }
}
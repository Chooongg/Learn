package com.chooongg.form.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.style.FormBoundary
import java.lang.ref.WeakReference

abstract class BaseFormProvider<T : BaseForm>(protected val manager: FormManager) {

    private var weakAdapter: WeakReference<FormAdapter>? = null

    lateinit var context: Context internal set

    abstract val itemViewType: Int

    abstract val layoutId: Int @LayoutRes get

    abstract fun onBindViewHolder(holder: FormViewHolder, boundary: FormBoundary, item: T)

    open fun onBindViewHolder(
        holder: FormViewHolder,
        boundary: FormBoundary,
        item: T,
        payloads: MutableList<Any>
    ) = onBindViewHolder(holder, boundary, item)

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val parentView = getStyle()?.createItemParentView(parent)
        return if (parentView != null) {
            LayoutInflater.from(parentView.context).inflate(layoutId, parentView, true)
            FormViewHolder(parentView)
        } else FormViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    open fun onViewHolderCreated(holder: FormViewHolder) = Unit

    @Suppress("UNCHECKED_CAST")
    @Deprecated("Please use onBindViewHolder(holder, item)")
    open fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = getAdapter()?.getItem(position) as? T ?: return
        val boundary = getAdapter()!!.getBoundary(position)
        getStyle()!!.onBindParentViewHolder(holder, boundary)
        onBindViewHolder(holder, boundary, item)
    }

    @Suppress("UNCHECKED_CAST")
    @Deprecated("Please use onBindViewHolder(holder, item, payloads)")
    open fun onBindViewHolder(holder: FormViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = getAdapter()?.getItem(position) as? T ?: return
        val boundary = getAdapter()!!.getBoundary(position)
        getStyle()!!.onBindParentViewHolder(holder, boundary, payloads)
        onBindViewHolder(holder, boundary, item, payloads)
    }

    internal fun setAdapter(adapter: FormAdapter) {
        weakAdapter = WeakReference(adapter)
    }

    protected fun getAdapter() = weakAdapter?.get()

    protected fun getStyle() = getAdapter()?.style
}
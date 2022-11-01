package com.chooongg.form.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.BaseForm
import java.lang.ref.WeakReference

abstract class BaseFormProvider<T : BaseForm>(protected val manager: FormManager) {

    private var weakAdapter: WeakReference<FormAdapter>? = null

    lateinit var context: Context internal set

    abstract val itemViewType: Int

    abstract val layoutId: Int @LayoutRes get

    abstract fun onBindViewHolder(holder: FormViewHolder, item: T)

    open fun onBindViewHolder(holder: FormViewHolder, item: T, payloads: MutableList<Any>) =
        onBindViewHolder(holder, item)

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return FormViewHolder(view)
    }

    open fun onViewHolderCreated(holder: FormViewHolder) = Unit

    @Suppress("UNCHECKED_CAST")
    @Deprecated("Please use onBindViewHolder(holder, item)")
    open fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = getAdapter()?.getItem(position) as? T ?: return
        onBindViewHolder(holder, item)
    }

    @Suppress("UNCHECKED_CAST")
    @Deprecated("Please use onBindViewHolder(holder, item, payloads)")
    open fun onBindViewHolder(holder: FormViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = getAdapter()?.getItem(position) as? T ?: return
        onBindViewHolder(holder, item, payloads)
    }

    internal fun setAdapter(adapter: FormAdapter) {
        weakAdapter = WeakReference(adapter)
    }

    protected fun getAdapter() = weakAdapter?.get()
}
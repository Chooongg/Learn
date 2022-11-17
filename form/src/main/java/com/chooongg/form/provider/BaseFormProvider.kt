package com.chooongg.form.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import java.lang.ref.WeakReference

abstract class BaseFormProvider<T : BaseForm>(protected val manager: FormManager) {

    lateinit var context: Context internal set

    private var _adapter: WeakReference<FormAdapter>? = null

    protected val adapter get() = _adapter?.get()

    protected val recyclerView get() = adapter?.recyclerView

    protected val style get() = adapter?.style

    abstract val itemViewType: Int

    abstract val layoutId: Int @LayoutRes get

    open val nameTextViewId: Int @IdRes get() = R.id.form_tv_name

    abstract fun onBindViewHolder(holder: FormViewHolder, item: T)

    open fun onBindViewHolder(
        holder: FormViewHolder,
        item: T,
        payloads: MutableList<Any>
    ) = onBindViewHolder(holder, item)

    open fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val parentView = style?.createItemParentView(parent)
        return if (parentView != null) {
            LayoutInflater.from(parentView.context).inflate(layoutId, parentView, true)
            FormViewHolder(parentView)
        } else FormViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    open fun onViewHolderCreated(holder: FormViewHolder) = Unit

    @Suppress("UNCHECKED_CAST")
    fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        val item = adapter?.getItem(position) as? T ?: return
        style?.apply {
            configNameTextView(manager, holder.getView(nameTextViewId), item)
            onBindParentViewHolder(holder, item)
        }
        onBindViewHolder(holder, item)
    }

    @Suppress("UNCHECKED_CAST")
    fun onBindViewHolder(holder: FormViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = adapter?.getItem(position) as? T ?: return
        style?.apply {
            configNameTextView(manager, holder.getViewOrNull(nameTextViewId), item)
            onBindParentViewHolder(holder, item, payloads)
        }
        onBindViewHolder(holder, item, payloads)
    }

    internal fun setAdapter(adapter: FormAdapter) {
        _adapter = WeakReference(adapter)
    }
}
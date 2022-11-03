package com.chooongg.form.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.setText
import com.chooongg.basic.ext.style
import com.chooongg.form.FormAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.style.FormBoundary
import java.lang.ref.WeakReference

abstract class BaseFormProvider<T : BaseForm>(protected val manager: FormManager) {

    private var weakAdapter: WeakReference<FormAdapter>? = null

    lateinit var context: Context internal set

    abstract val itemViewType: Int

    abstract val layoutId: Int @LayoutRes get

    open val nameTextViewId: Int @IdRes get() = R.id.form_tv_name

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
        configNameTextView(holder.getView(nameTextViewId), item)
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

    protected fun configNameTextView(textView: TextView, item: T) {
        if (item.keepNameEms) {
            textView.minWidth = 0
        } else {
            textView.setEms(manager.nameEmsSize)
        }
        if (item.isMust) {
            textView.setText(item.name.style {} + "*".style {
                setForegroundColor(textView.attrColor(androidx.appcompat.R.attr.colorError))
            })
        } else textView.text = item.name
    }

    internal fun setAdapter(adapter: FormAdapter) {
        weakAdapter = WeakReference(adapter)
    }

    protected fun getAdapter() = weakAdapter?.get()

    protected fun getStyle() = getAdapter()?.style
}
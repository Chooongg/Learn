package com.chooongg.form.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.*
import com.chooongg.form.*
import com.chooongg.form.bean.BaseForm
import com.google.android.material.button.MaterialButton
import java.lang.ref.WeakReference
import kotlin.math.max

abstract class BaseFormProvider<T : BaseForm>(protected val manager: BaseFormManager) {

    lateinit var context: Context internal set

    private var _groupAdapter: WeakReference<FormGroupAdapter>? = null

    protected val groupAdapter get() = _groupAdapter?.get()

    protected val recyclerView get() = groupAdapter?.recyclerView

    protected val style get() = groupAdapter?.style

    abstract val itemViewType: Int

    abstract val layoutId: Int @LayoutRes get

    open val nameTextViewId: Int @IdRes get() = R.id.form_tv_name

    abstract fun onBindViewHolder(holder: FormViewHolder, item: T)

    open fun onBindViewHolder(
        holder: FormViewHolder, item: T, payloads: MutableList<Any>
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
        val item = groupAdapter?.getItem(position) as? T ?: return
        configItemClick(holder, item)
        configMenuIcon(holder, item)
        style?.apply {
            onBindParentViewHolder(manager, holder, item)
            configNameTextView(manager, holder.getView(nameTextViewId), item)
        }
        onBindViewHolder(holder, item)
    }

    @Suppress("UNCHECKED_CAST")
    fun onBindViewHolder(holder: FormViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = groupAdapter?.getItem(position) as? T ?: return
        configItemClick(holder, item)
        configMenuIcon(holder, item)
        style?.apply {
            onBindParentViewHolder(manager, holder, item, payloads)
            configNameTextView(manager, holder.getViewOrNull(nameTextViewId), item)
        }
        onBindViewHolder(holder, item, payloads)
    }

    /**
     * 配置点击事件
     */
    open fun configItemClick(holder: FormViewHolder, item: T) {
        holder.itemView.setOnClickListener {
            groupAdapter?.clearFocus()
            groupAdapter?.onFormClick(manager, item, it, holder.absoluteAdapterPosition)
        }
    }

    /**
     * 配置菜单图标
     */
    open fun configMenuIcon(holder: FormViewHolder, item: T) {
        holder.getViewOrNull<MaterialButton>(R.id.form_iv_menu)?.apply {
            if (item.menuIcon != null && item.isRealMenuVisible(manager)) {
                isEnabled = item.isRealMenuEnable(manager)
                setIconResource(item.menuIcon!!)
                iconTint = item.menuIconTint?.invoke(context)
                doOnClick {
                    groupAdapter?.clearFocus()
                    groupAdapter?.onFormMenuClick(
                        manager, item, this, holder.absoluteAdapterPosition
                    )
                }
                visible()
            } else gone()
        }
    }

    internal fun setAdapter(adapter: FormGroupAdapter) {
        _groupAdapter = WeakReference(adapter)
    }
}
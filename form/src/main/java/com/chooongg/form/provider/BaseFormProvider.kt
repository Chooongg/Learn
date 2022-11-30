package com.chooongg.form.provider

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.chooongg.basic.ext.*
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import java.lang.ref.WeakReference
import kotlin.math.max

abstract class BaseFormProvider<T : BaseForm>(protected val manager: FormManager) {

    lateinit var context: Context internal set

    private var _adapter: WeakReference<FormGroupAdapter>? = null

    protected val adapter get() = _adapter?.get()

    protected val recyclerView get() = adapter?.recyclerView

    protected val style get() = adapter?.style

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
        val item = adapter?.getItem(position) as? T ?: return
        val groupIndex = manager.adapter.adapters.indexOf(adapter)
        logE("Form","onBindViewHolder(${groupIndex}|${position})")
        configItemClick(holder, item)
        configMenuIcon(holder, item)
        style?.apply {
            configNameTextView(manager, holder.getView(nameTextViewId), item)
            onBindParentViewHolder(manager, holder, item)
        }
        onBindViewHolder(holder, item)
    }

    @Suppress("UNCHECKED_CAST")
    fun onBindViewHolder(holder: FormViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = adapter?.getItem(position) as? T ?: return
        val groupIndex = manager.adapter.adapters.indexOf(adapter)
        logE("Form","onBindViewHolder(${groupIndex}|${position}) payloads")
        configItemClick(holder, item)
        configMenuIcon(holder, item)
        style?.apply {
            configNameTextView(manager, holder.getViewOrNull(nameTextViewId), item)
            onBindParentViewHolder(manager, holder, item, payloads)
        }
        onBindViewHolder(holder, item, payloads)
    }

    /**
     * 配置点击事件
     */
    open fun configItemClick(holder: FormViewHolder, item: T) {
        holder.itemView.doOnClick {
            recyclerView?.clearFocus()
            adapter?.onFormClick(manager, item, it, holder.absoluteAdapterPosition)
        }
    }

    /**
     * 配置菜单图标
     */
    open fun configMenuIcon(holder: FormViewHolder, item: T) {
        holder.getViewOrNull<AppCompatImageView>(R.id.form_iv_menu)?.apply {
            if (item.menuIcon != null && item.isRealMenuVisible(manager)) {
                updateLayoutParams<ConstraintLayout.LayoutParams> {
                    marginEnd = max(0, manager.itemHorizontalSize - paddingEnd)
                    width =
                        resDimensionPixelSize(R.dimen.formItemMenuIconSize) + paddingStart + paddingEnd
                }
                setImageResource(item.menuIcon!!)
                imageTintList = item.menuIconTint?.invoke(context)
                doOnClick {
                    recyclerView?.clearFocus()
                    adapter?.onFormMenuClick(manager, item, this, holder.absoluteAdapterPosition)
                }
                visible()
            } else gone()
        }
    }

    internal fun setAdapter(adapter: FormGroupAdapter) {
        _adapter = WeakReference(adapter)
    }
}
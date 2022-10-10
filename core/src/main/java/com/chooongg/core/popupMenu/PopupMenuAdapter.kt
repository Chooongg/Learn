package com.chooongg.core.popupMenu

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.gone
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.visible
import com.chooongg.core.R
import com.chooongg.core.popupMenu.model.PopupMenuCustomItem
import com.chooongg.core.popupMenu.model.PopupMenuItem
import com.chooongg.core.popupMenu.model.PopupMenuSection
import com.google.android.material.divider.MaterialDivider

internal class PopupMenuAdapter(
    private val isForceShowIcon: Boolean,
    private val sections: List<PopupMenuSection>,
    private val dismissPopupCallback: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun getItemViewType(position: Int) = when (val any = getItem(position)) {
        is PopupMenuSection -> R.layout.learn_popup_menu_section_header
        is PopupMenuItem -> R.layout.learn_popup_menu_item
        is PopupMenuCustomItem -> any.layoutResId
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(viewType, parent, false)
    )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val any = getItem(position)
        when (holder.itemViewType) {
            R.layout.learn_popup_menu_section_header -> {
                val item = any as PopupMenuSection
                with(holder.getView<TextView>(R.id.tv_title)) {
                    text = item.title
                    if (item.title.isNullOrEmpty()) gone() else visible()
                }
                with(holder.getView<MaterialDivider>(R.id.divider)) {
                    if (position == 0) gone() else visible()
                }
            }
            R.layout.learn_popup_menu_item -> {
                val item = any as PopupMenuItem
                with(holder.getView<TextView>(R.id.tv_label)) {
                    if (item.label != null) {
                        text = item.label
                    } else if (item.labelRes != null) {
                        setText(item.labelRes!!)
                    } else text = null
                    if (item.labelColor != null) {
                        setTextColor(item.labelColor!!)
                    } else setTextColor(attrColor(com.google.android.material.R.attr.colorOnSurface))
                    updateLayoutParams<LinearLayoutCompat.LayoutParams> {
                        marginStart = if (item.iconDrawable != null
                            || item.icon != null
                            || isForceShowIcon
                        ) 0 else resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium)
                        marginEnd = if (!item.hasNestedItems) {
                            resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium)
                        } else 0
                    }
                }
                with(holder.getView<AppCompatImageView>(R.id.iv_icon)) {
                    imageTintList = if (item.isDefaultTint) {
                        ColorStateList.valueOf(attrColor(androidx.appcompat.R.attr.colorControlNormal))
                    } else item.iconTint
                    if (item.iconDrawable != null) {
                        setImageDrawable(item.iconDrawable)
                        visible()
                    } else if (item.icon != null) {
                        setImageResource(item.icon!!)
                        visible()
                    } else if (isForceShowIcon) {
                        setImageDrawable(null)
                        visible()
                    } else {
                        gone()
                    }
                }
                with(holder.getView<AppCompatImageView>(R.id.iv_nested)) {
                    if (item.hasNestedItems) visible() else gone()
                }
                item.onViewBindCallback?.invoke(holder.itemView)
                holder.itemView.setOnClickListener {
                    item.onSelectedCallback?.invoke()
                    if (item.selectedAutoDismiss) dismissPopupCallback()
                }
            }
            else -> {
                val item = any as PopupMenuCustomItem
                item.onViewBindCallback?.invoke(holder.itemView)
                holder.itemView.setOnClickListener {
                    item.onSelectedCallback?.invoke()
                    if (item.selectedAutoDismiss) dismissPopupCallback()
                }
            }
        }
    }

    private fun getItem(position: Int): Any {
        var index = 0
        for (section in sections) {
            if (index == position) {
                return section
            }
            index++
            for (item in section.items) {
                if (index == position) {
                    return item
                }
                index++
            }
        }
        throw IllegalArgumentException("Invalid position")
    }

    override fun getItemCount(): Int {
        var count = 0
        for (section in sections) {
            count++
            count += section.items.size
        }
        return count
    }
}
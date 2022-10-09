package com.chooongg.core.popupMenu2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.core.R
import com.chooongg.core.popupMenu.LearnPopupMenu
import com.chooongg.core.popupMenu2.model.PopupMenuCustomItem
import com.chooongg.core.popupMenu2.model.PopupMenuItem
import com.chooongg.core.popupMenu2.model.PopupMenuSection

internal class PopupMenuAdapter(
    private val sections: List<LearnPopupMenu.PopupMenuSection>,
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
            }
            R.layout.learn_popup_menu_item -> {
                val item = any as PopupMenuItem
                item.onViewBindCallback?.invoke(holder.itemView)
            }
            else -> {
                val item = any as PopupMenuCustomItem
                item.onViewBindCallback?.invoke(holder.itemView)
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
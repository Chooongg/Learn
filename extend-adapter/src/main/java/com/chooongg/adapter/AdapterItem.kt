package com.chooongg.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

typealias AdapterItem = Item<*, *>

interface Item<T, HOLDER : RecyclerView.ViewHolder> {

    fun isWithinViewType(item: T, position: Int) = true

    fun onCreateViewHolder(parent: ViewGroup): HOLDER

    fun onBindViewHolder(holder: HOLDER, item: T, position: Int)
    fun onBindViewHolder(holder: HOLDER, item: T, position: Int, payloads: List<Any>) {
        onBindViewHolder(holder, item, position)
    }

    fun onViewRecycled(holder: HOLDER)

    fun onFailedToRecycleView(holder: HOLDER): Boolean

    fun onViewAttachedToWindow(holder: HOLDER)
    fun onViewDetachedFromWindow(holder: HOLDER)

    fun onAttachedToRecyclerView(recyclerView: RecyclerView)
    fun onDetachedFromRecyclerView(recyclerView: RecyclerView)

    fun getItemViewType() = 0
}
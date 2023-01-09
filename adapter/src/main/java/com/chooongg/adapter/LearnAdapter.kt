package com.chooongg.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.adapter.viewHolder.BaseViewHolder

abstract class LearnAdapter<DATA, HOLDER : BaseViewHolder>(data: MutableList<DATA>? = null) :
    RecyclerView.Adapter<HOLDER>() {

    protected var data: MutableList<DATA> = mutableListOf()

    val currentData: List<DATA> get() = data

    init {
        this.data = data ?: mutableListOf()
    }

    final override fun onBindViewHolder(holder: HOLDER, position: Int) {
        onBind(holder, position)
    }

    final override fun onBindViewHolder(holder: HOLDER, position: Int, payloads: MutableList<Any>) {
        onBind(holder, position, payloads)
    }

    abstract fun onBind(holder: HOLDER, position: Int)

    open fun onBind(holder: HOLDER, position: Int, payloads: MutableList<Any>) {}

    override fun getItemCount() = data.size
}
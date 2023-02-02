package com.chooongg.adapter

import com.chooongg.adapter.viewHolder.BaseViewHolder


abstract class LearnSingleAdapter<DATA, HOLDER : BaseViewHolder>(data: MutableList<DATA>? = null) :
    LearnAdapter<DATA, HOLDER>(data) {

    override fun getItemCount() = if (data.isEmpty()) 0 else 1
}
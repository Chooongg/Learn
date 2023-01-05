package com.chooongg.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.adapter.viewHolder.BaseViewHolder

abstract class BaseAdapter<DATA, HOLDER : BaseViewHolder>(data: List<DATA>) :
    RecyclerView.Adapter<HOLDER>() {

    private var currentData: MutableList<DATA> = mutableListOf()

    val data: List<DATA> get() = currentData

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HOLDER {
    }

    final override fun onBindViewHolder(holder: HOLDER, position: Int) {
        onBind(holder, position)
    }

    final override fun onBindViewHolder(holder: HOLDER, position: Int, payloads: MutableList<Any>) {
        onBind(holder, position, payloads)
    }

    abstract fun onBind(holder: HOLDER, position: Int)

    open fun onBind(holder: HOLDER, position: Int, payloads: MutableList<Any>) {}

    override fun getItemCount() = currentData.size
}
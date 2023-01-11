package com.chooongg.adapter

import android.view.View
import com.chooongg.adapter.listener.OnItemClickListener
import com.chooongg.adapter.viewHolder.BaseViewHolder

class Test:OnItemClickListener<String> {
    init {
        val adapter = object : BaseAdapter<String>(android.R.layout.list_content) {
            override fun onBind(holder: BaseViewHolder, position: Int) {

            }
        }
        adapter.setOnItemClickListener{ a, view, position ->

        }
    }
}
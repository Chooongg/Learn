package com.chooongg.mediaPicker.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MediaListAdapter(
    @androidx.annotation.IntRange(from = 1) val maxCount: Int,
    isEditable: Boolean = false
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val async = AsyncListDiffer<String>(this,object:DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        }
    })

    var isEditable: Boolean = isEditable
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    var isForceLockMaxCount: Boolean = false
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    private val data: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    }


    override fun getItemCount() = data.size
}
package com.chooongg.mediaPicker.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.mediaPicker.bean.MediaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class MediaListAdapter(
    @androidx.annotation.IntRange(from = 1) val maxCount: Int,
    isEditable: Boolean = false
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val async = AsyncListDiffer(this, object : DiffUtil.ItemCallback<MediaEntity>() {
        override fun areItemsTheSame(oldItem: MediaEntity, newItem: MediaEntity): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: MediaEntity, newItem: MediaEntity): Boolean {
            return oldItem == newItem
        }
    })

    var isEditable: Boolean = isEditable
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    private val data: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        TODO()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    }


    override fun getItemCount() = data.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
    }
}
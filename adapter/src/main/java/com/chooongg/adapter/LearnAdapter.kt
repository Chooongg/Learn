package com.chooongg.adapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.adapter.listener.*
import com.chooongg.adapter.viewHolder.BaseViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class LearnAdapter<DATA, HOLDER : BaseViewHolder>(data: MutableList<DATA>? = null) :
    RecyclerView.Adapter<HOLDER>() {

    protected var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    protected var data: MutableList<DATA> = mutableListOf()

    val currentData: List<DATA> get() = data

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null
    private val onItemChildClickList = SparseArray<OnItemChildClickListener>()
    private val onItemChildLongClickList = SparseArray<OnItemChildLongClickListener>()

    var isAttachedToRecyclerView: Boolean = false
        private set

    private var _recyclerView: RecyclerView? = null

    val recyclerView: RecyclerView
        get() {
            checkNotNull(_recyclerView) { "Please get it after onAttachedToRecyclerView()" }
            return _recyclerView!!
        }

    val context: Context get() = recyclerView.context

    init {
        this.data = data ?: mutableListOf()
    }

    override fun getItemCount() = data.size

    abstract fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): HOLDER

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HOLDER {
        val holder = onCreateViewHolder(parent.context, parent, viewType)
        onItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                val position = holder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener
                listener.onItemClick(this, holder.itemView, position)
            }
        }
        onItemLongClickListener?.let { listener ->
            holder.itemView.setOnLongClickListener {
                val position = holder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnLongClickListener false
                listener.onItemLongClick(this, holder.itemView, position)
            }
        }
        return holder
    }

    abstract fun onBind(holder: HOLDER, position: Int)

    open fun onBind(holder: HOLDER, position: Int, payloads: MutableList<Any>) {}

    final override fun onBindViewHolder(holder: HOLDER, position: Int) {
        onBind(holder, position)
    }

    final override fun onBindViewHolder(holder: HOLDER, position: Int, payloads: MutableList<Any>) {
        onBind(holder, position, payloads)
    }

    fun submitData(list: List<DATA>?, callback: DiffCallback<DATA>) {
        if (!isAttachedToRecyclerView) {
            data = list?.toMutableList() ?: mutableListOf()
            return
        }
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = data.size
            override fun getNewListSize() = list?.size ?: 0
            override fun areItemsTheSame(
                oldItemPosition: Int, newItemPosition: Int
            ) = callback.areItemsTheSame(data[oldItemPosition], list!![newItemPosition])

            override fun areContentsTheSame(
                oldItemPosition: Int, newItemPosition: Int
            ) = callback.areContentsTheSame(data[oldItemPosition], list!![newItemPosition])
        })
        data = list?.toMutableList() ?: mutableListOf()
        result.dispatchUpdatesTo(this@LearnAdapter)
    }

    fun addAll(list: List<DATA>?) {
        if (list.isNullOrEmpty()) return
        val oldSize = data.size
        data.addAll(list)
        notifyItemRangeInserted(oldSize, list.size)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        isAttachedToRecyclerView = true
        _recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = null
        resetScope()
        isAttachedToRecyclerView = false
    }

    fun resetScope() {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    protected open fun onItemClick(view: View, position: Int) =
        onItemClickListener?.onItemClick(this, view, position)

    protected open fun onItemLongClick(view: View, position: Int) =
        onItemLongClickListener?.onItemLongClick(this, view, position)

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        onItemLongClickListener = listener
    }
}
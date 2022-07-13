package com.chooongg.adapter.viewHolder

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BasicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val views: SparseArray<View> = SparseArray()

    open fun <T:View> getView(id: Int): T {
        var view = views[id]
        if (view == null) {
            view = itemView.findViewById(id)
            views.put(id, view)
        }
        return view as T
    }
}
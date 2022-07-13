package com.chooongg.adapter.items

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.adapter.IIdentifyable

interface IItem<HOLDER : RecyclerView.ViewHolder> : IIdentifyable {
}
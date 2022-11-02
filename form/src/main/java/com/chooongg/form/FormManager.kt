package com.chooongg.form

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle

class FormManager(isEditable: Boolean, val formStyle: FormStyle) {

    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_ADDRESS = 1
        const val TYPE_BUTTON = 2
        const val TYPE_CHECKBOX = 3
        const val TYPE_DIVIDER = 4
        const val TYPE_FILE = 5
        const val TYPE_GROUP_LABEL = 6
        const val TYPE_INPUT = 7
        const val TYPE_INPUT_AUTO_COMPLETE = 8
        const val TYPE_LABEL = 9
        const val TYPE_MENU = 10
        const val TYPE_RADIO = 11
        const val TYPE_RATE = 12
        const val TYPE_SELECT = 13
        const val TYPE_SLIDER = 14
        const val TYPE_SWITCH = 15
        const val TYPE_TIME = 16
        const val TYPE_TIME_RANGE = 17
        const val TYPE_TIP = 18
    }

    internal val adapter =
        ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    var isEditable: Boolean = isEditable
        set(value) {
            if (field == value) return
            field = value
            if (adapter.itemCount > 0) {
                adapter.notifyItemRangeChanged(0, adapter.itemCount)
            }
        }

    fun attach(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
    }

    fun addGroup(style: FormStyle = DefaultFormStyle(), block: FormAdapter.() -> Unit) {
        adapter.addAdapter(FormAdapter(this, style).apply(block))
    }
}
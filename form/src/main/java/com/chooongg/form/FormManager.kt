package com.chooongg.form

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.style.FormStyle

class FormManager(isEditable: Boolean, val formStyle: FormStyle) {

    companion object{
        const val TEXT = 0
        const val ADDRESS = 1
        const val BUTTON = 2
        const val CHECKBOX = 3
        const val DIVIDER = 4
        const val FILE = 5
        const val GROUP_LABEL = 6
        const val INPUT = 7
        const val INPUT_AUTO_COMPLETE = 8
        const val LABEL = 9
        const val MENU = 10
        const val RADIO = 11
        const val RATE = 12
        const val SELECT = 13
        const val SLIDER = 14
        const val SWITCH = 15
        const val TIME = 16
        const val TIME_RANGE = 17
        const val TIP = 18
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
}
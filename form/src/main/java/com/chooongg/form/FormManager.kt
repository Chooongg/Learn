package com.chooongg.form

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.style.CardFormStyle
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle

class FormManager(isEditable: Boolean, block: (FormManager.() -> Unit)? = null) {



    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_ADDRESS = 1
        const val TYPE_BUTTON = 2
        const val TYPE_CHECKBOX = 3
        const val TYPE_DIVIDER = 4
        const val TYPE_FILE = 5
        const val TYPE_GROUP_NAME = 6
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

    internal val adapter = ConcatAdapter(
        ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
    )

    var isEditable: Boolean = isEditable
        set(value) {
            if (field == value) return
            field = value
            update("update")
        }

    var nameEmsSize: Int = 7
        set(value) {
            if (field == value) return
            field = value
            update("update")
        }

    var nameMustHasHint: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            update("update")
        }

    init {
        block?.invoke(this)
    }

    fun attach(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
    }

    fun update(payload: Any? = null) {
        if (adapter.itemCount > 0) adapter.notifyItemRangeChanged(0, adapter.itemCount, payload)
    }

    fun findItemForField(field: String, changeBlock: BaseForm.() -> Unit) {
        var index = 0
        adapter.adapters.forEach { adapter ->
            if (adapter is FormAdapter) {
                adapter.data.forEach { part ->
                    if (adapter.hasGroupName) index++
                    part.forEach {
                        if (it.field == field) {
                            changeBlock(it)
                            if (it.isRealVisible(this)) {
                                adapter.notifyItemChanged(index, "update")
                            }
                            return
                        }
                        index++
                    }
                }
            }
        }
    }

    fun findItemForName(name: CharSequence, changeBlock: BaseForm.() -> Unit) {
        var index = 0
        adapter.adapters.forEach { adapter ->
            if (adapter is FormAdapter) {
                adapter.data.forEach { part ->
                    if (adapter.hasGroupName) index++
                    part.forEach {
                        if (it.name == name) {
                            changeBlock(it)
                            if (it.isRealVisible(this)) {
                                adapter.notifyItemChanged(index, "update")
                            }
                            return
                        }
                        index++
                    }
                }
            }
        }
    }

    fun addPart(style: FormStyle = DefaultFormStyle(), block: FormAdapter.() -> Unit) {
        adapter.addAdapter(FormAdapter(this, style).apply(block))
    }

    fun addCardPart(block: FormAdapter.() -> Unit) {
        addPart(CardFormStyle(), block)
    }
}
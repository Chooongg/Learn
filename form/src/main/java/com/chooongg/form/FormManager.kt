package com.chooongg.form

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.style.CardFormStyle
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle

class FormManager(isEditable: Boolean, block: (FormManager.() -> Unit)? = null) {

    companion object {
        const val TYPE_GROUP_NAME = 0
        const val TYPE_TEXT = 1
        const val TYPE_ADDRESS = 2
        const val TYPE_BUTTON = 3
        const val TYPE_CHECKBOX = 4
        const val TYPE_DIVIDER = 5
        const val TYPE_FILE = 6
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
            updateAll()
        }

    var nameEmsSize: Int = 6
        set(value) {
            if (field == value) return
            field = value
            updateAll()
        }

    var nameMustHasHint: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            updateAll()
        }

    init {
        block?.invoke(this)
    }

    fun attach(owner: LifecycleOwner, recyclerView: RecyclerView) {
        if (owner.lifecycle.currentState < Lifecycle.State.INITIALIZED) return
        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }
        recyclerView.adapter = adapter
        owner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (owner.lifecycle.currentState <= Lifecycle.State.DESTROYED) {
                    owner.lifecycle.removeObserver(this)
                    recyclerView.adapter = null
                }
            }
        })
    }

    fun updateAll() {
        adapter.adapters.forEach {
            if (it is FormAdapter) {
                it.update()
            }
        }
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

    fun getItemCount(): Int {
        var count = 0
        adapter.adapters.forEach { part ->
            if (part is FormAdapter) {
                part.data.forEach { group ->
                    count += group.size
                }
            }
        }
        return count
    }

    fun addPart(style: FormStyle = DefaultFormStyle(), block: FormAdapter.() -> Unit) {
        val part = FormAdapter(this, style).apply(block)
        adapter.addAdapter(part)
        adapter.adapters.forEach {
            if (it != part && it is FormAdapter) {
                it.update()
            }
        }
    }

    fun addCardPart(block: FormAdapter.() -> Unit) {
        addPart(CardFormStyle(), block)
    }
}
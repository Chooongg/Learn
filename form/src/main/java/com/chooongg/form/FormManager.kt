package com.chooongg.form

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.style.CardFormStyle
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle

class FormManager(isEditable: Boolean) {

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

    /**
     * 是否可编辑
     */
    var isEditable: Boolean = isEditable
        set(value) {
            if (field == value) return
            field = value
            updateAll()
        }

    /**
     * 名称占位符长度
     */
    var nameEmsSize: Int = 6
        set(value) {
            if (field == value) return
            field = value
            updateAll()
        }

    /**
     * 必填时有星号
     *
     */
    var whenMustHasAsterisk: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            updateAll()
        }

    internal var formEventListener: FormEventListener? = null
        set(value) {
            field = value
            adapter.adapters.forEach {
                if (it is FormGroupAdapter) it.formEventListener = formEventListener
            }
        }

    var itemHorizontalSize = 0
        private set
    var itemVerticalSize = 0
        private set
    var itemVerticalEdgeSize = 0
        private set

    fun attach(
        owner: LifecycleOwner, recyclerView: RecyclerView, listener: FormEventListener? = null
    ) {
        if (owner.lifecycle.currentState < Lifecycle.State.INITIALIZED) return
        itemHorizontalSize = recyclerView.resDimensionPixelSize(R.dimen.formItemHorizontal)
        itemVerticalSize = recyclerView.resDimensionPixelSize(R.dimen.formItemVertical)
        itemVerticalEdgeSize = recyclerView.resDimensionPixelSize(R.dimen.formItemVerticalEdge)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
        formEventListener = listener
        owner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (owner.lifecycle.currentState <= Lifecycle.State.DESTROYED) {
                    owner.lifecycle.removeObserver(this)
                    formEventListener = null
                    recyclerView.adapter = null
                }
            }
        })
    }

    fun updateAll() {
        adapter.adapters.forEach {
            if (it is FormGroupAdapter) {
                it.update()
            }
        }
    }

    fun getItemCount(): Int {
        var count = 0
        adapter.adapters.forEach { group ->
            if (group is FormGroupAdapter) {
                group.data.forEach { part ->
                    count += part.size
                }
            }
        }
        return count
    }

    fun addGroup(style: FormStyle = DefaultFormStyle(), block: FormCreateGroup.() -> Unit) {
        val group = FormGroupAdapter(this, style)
        group.formEventListener = formEventListener
        adapter.addAdapter(group)
        group.setNewList(block)
    }

    fun addCardGroup(block: FormCreateGroup.() -> Unit) {
        addGroup(CardFormStyle(), block)
    }

    fun findItemForField(field: String, changeBlock: BaseForm.() -> Unit) {
        var index = 0
        adapter.adapters.forEach { group ->
            if (group is FormGroupAdapter) {
                group.data.forEach { part ->
                    if (group.hasGroupTitle) index++
                    part.forEach {
                        if (it.field == field) {
                            changeBlock(it)
                            if (it.isRealVisible(this)) {
                                group.notifyItemChanged(index, "update")
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
        adapter.adapters.forEach { group ->
            if (group is FormGroupAdapter) {
                group.data.forEach { part ->
                    if (group.hasGroupTitle) index++
                    part.forEach {
                        if (it.name == name) {
                            changeBlock(it)
                            if (it.isRealVisible(this)) {
                                group.notifyItemChanged(index, "update")
                            }
                            return
                        }
                        index++
                    }
                }
            }
        }
    }

    fun setOnFormEventListener(listener: FormEventListener? = null) {
        formEventListener = listener
    }

    fun notifyItemChanged(position: Int) {
        adapter.notifyItemChanged(position, "update")
    }
}
package com.chooongg.form

import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.hideIME
import com.chooongg.core.widget.layoutManager.CenterScrollLinearLayoutManager
import com.chooongg.form.config.FormManagerConfig
import com.chooongg.form.creator.FormAdapterCreator
import com.chooongg.form.style.FormStyle
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

class FormManager(
    isEditable: Boolean,
    @androidx.annotation.IntRange(from = 1) nameEmsSize: Int = FormManagerConfig.defaultNameEmsSize
) : BaseFormManager(isEditable, nameEmsSize) {

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
        const val TYPE_RATING = 12
        const val TYPE_SELECT = 13
        const val TYPE_SLIDER = 14
        const val TYPE_SWITCH = 15
        const val TYPE_TIME = 16
        const val TYPE_TIP = 17

        fun config(block: FormManagerConfig.() -> Unit) {
            FormManagerConfig.block()
        }
    }

    internal var formEventListener: FormEventListener? = null
        set(value) {
            field = value
            adapter.adapters.forEach {
                if (it is FormGroupAdapter) it.setFormEventListener(formEventListener)
            }
        }

    internal val styleSequence = CopyOnWriteArraySet<KClass<out FormStyle>>()

    fun init(
        recyclerView: RecyclerView, listener: FormEventListener? = null
    ) {
        initSize(recyclerView.context)
        _recyclerView = WeakReference(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING && recyclerView.focusedChild != null) {
                    recyclerView.focusedChild.clearFocus()
                    recyclerView.context.hideIME()
                }
            }
        })
        recyclerView.layoutManager = CenterScrollLinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
        formEventListener = listener
    }

//    fun addGroup(style: FormStyle = DefaultFormStyle(), block: FormGroupCreator.() -> Unit) {
//        if (!styleSequence.contains(style::class)) {
//            styleSequence.add(style::class)
//        }
//        val group = FormGroupAdapter(this, style, styleSequence.indexOf(style::class) * 100)
//        group.setFormEventListener(formEventListener)
//        val count = adapter.adapters.size
//        adapter.addAdapter(group)
//        group.setNewList(block)
//        if (count > 0) {
//            val previousGroup = adapter.adapters[count - 1] as? FormGroupAdapter
//            previousGroup?.update(true)
//        }
//    }
//
//    fun addMaterialCardGroup(block: FormGroupCreator.() -> Unit) {
//        addGroup(Material3CardFormStyle(), block)
//    }

    fun setNewData(block: FormAdapterCreator.() -> Unit) {
        val creator = FormAdapterCreator(this@FormManager).apply(block)
        setNewData(creator)
    }

    fun setNewData(creator: FormAdapterCreator) {
        adapter.adapters.forEach { adapter.removeAdapter(it) }
        creator.adapters.forEachIndexed { index, group ->
            adapter.addAdapter(group)
            if (index > 1) {
                (adapter.adapters[index - 1] as? FormGroupAdapter)?.update(true)
            }
        }
    }

    fun setOnFormEventListener(listener: FormEventListener? = null) {
        formEventListener = listener
    }
}
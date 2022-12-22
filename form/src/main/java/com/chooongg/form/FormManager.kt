package com.chooongg.form

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.hideIME
import com.chooongg.basic.ext.logE
import com.chooongg.core.widget.layoutManager.CenterScrollLinearLayoutManager
import com.chooongg.form.config.FormManagerConfig
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle
import com.chooongg.form.style.MaterialCardFormStyle
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.reflect.KClass

class FormManager(
    isEditable: Boolean,
    @androidx.annotation.IntRange(from = 1) nameEmsSize: Int = FormManagerConfig.nameEmsSize
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

    private val styleSequence = CopyOnWriteArraySet<KClass<out FormStyle>>()

    fun init(
        owner: LifecycleOwner, recyclerView: RecyclerView, listener: FormEventListener? = null
    ) {
        if (owner.lifecycle.currentState <= Lifecycle.State.DESTROYED) return
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

    fun addGroup(style: FormStyle = DefaultFormStyle(), block: FormCreateGroup.() -> Unit) {
        if (!styleSequence.contains(style::class)) {
            styleSequence.add(style::class)
        }
        val group = FormGroupAdapter(this, style, styleSequence.indexOf(style::class) * 100)
        group.setFormEventListener(formEventListener)
        val count = adapter.adapters.size
        adapter.addAdapter(group)
        group.setNewList(block)
        if (count > 0) {
            val previousGroup = adapter.adapters[count - 1] as? FormGroupAdapter
            previousGroup?.update(true)
        }
    }

    fun addMaterialCardGroup(block: FormCreateGroup.() -> Unit) {
        addGroup(MaterialCardFormStyle(), block)
    }

    fun setOnFormEventListener(listener: FormEventListener? = null) {
        formEventListener = listener
    }
}
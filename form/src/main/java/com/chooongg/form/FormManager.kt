package com.chooongg.form

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.showToast
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle
import com.chooongg.form.style.MaterialCardFormStyle
import org.json.JSONObject
import java.lang.ref.WeakReference

class FormManager(isEditable: Boolean, nameEmsSize: Int = 6) {

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

    private var _recyclerView: WeakReference<RecyclerView>? = null

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
    var nameEmsSize: Int = nameEmsSize
        set(value) {
            if (field == value) return
            field = value
            updateAll(true)
        }

    /**
     * 必填时有星号
     *
     */
    var whenMustHasAsterisk: Boolean = true
        set(value) {
            if (field == value) return
            field = value
            updateAll(true)
        }

    internal var formEventListener: FormEventListener? = null
        set(value) {
            field = value
            adapter.adapters.forEach {
                if (it is FormGroupAdapter) it.setFormEventListener(formEventListener)
            }
        }

    private var groupHistoryCount = 0

    var itemHorizontalSize = 0
        private set
    var itemVerticalSize = 0
        private set
    var itemVerticalEdgeSize = 0
        private set

    fun init(
        owner: LifecycleOwner, recyclerView: RecyclerView, listener: FormEventListener? = null
    ) {
        if (owner.lifecycle.currentState < Lifecycle.State.INITIALIZED) return
        itemHorizontalSize = recyclerView.resDimensionPixelSize(R.dimen.formItemHorizontal)
        itemVerticalSize = recyclerView.resDimensionPixelSize(R.dimen.formItemVertical)
        itemVerticalEdgeSize = recyclerView.resDimensionPixelSize(R.dimen.formItemVerticalEdge)
        _recyclerView = WeakReference(recyclerView)
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

    fun updateAll(isHasPayloads: Boolean = false) {
        adapter.adapters.forEach {
            if (it is FormGroupAdapter) {
                it.update(isHasPayloads)
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

    fun clearAll() {
        for (i in adapter.adapters.lastIndex downTo 0) {
            adapter.removeAdapter(adapter.adapters[i])
        }
        groupHistoryCount = 0
    }

    fun addGroup(style: FormStyle = DefaultFormStyle(), block: FormCreateGroup.() -> Unit) {
        val group = FormGroupAdapter(this, style, groupHistoryCount)
        groupHistoryCount += 100
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

    fun findItemForFieldAndPartPosition(
        field: String, partPosition: Int, changeBlock: BaseForm.() -> Unit
    ) {
        var index = 0
        adapter.adapters.forEach { group ->
            if (group is FormGroupAdapter) {
                group.data.forEach { part ->
                    if (group.hasGroupTitle) index++
                    part.forEach {
                        if (it.field == field && it.partPosition == partPosition) {
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

    fun findItemForNameAndPartPosition(
        name: CharSequence, partPosition: Int, changeBlock: BaseForm.() -> Unit
    ) {
        var index = 0
        adapter.adapters.forEach { group ->
            if (group is FormGroupAdapter) {
                group.data.forEach { part ->
                    if (group.hasGroupTitle) index++
                    part.forEach {
                        if (it.name == name && it.partPosition == partPosition) {
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

    /**
     * 检查数据正确性
     */
    fun checkDataCorrectness(): Boolean {
        try {
            adapter.adapters.forEach { if (it is FormGroupAdapter) it.checkDataCorrectness() }
            return true
        } catch (e: FormDataVerificationException) {
            showToast(e.message)
            var globalPosition = 0
            adapter@ for (i in 0 until adapter.adapters.size) {
                if (adapter.adapters[i] !is FormGroupAdapter) {
                    globalPosition += adapter.adapters[i].itemCount
                    continue@adapter
                }
                val itemAdapter = adapter.adapters[i] as FormGroupAdapter
                for (j in 0 until itemAdapter.itemCount) {
                    val item = itemAdapter.getItem(j)
                    if (item.field == e.field) {
                        _recyclerView?.get()?.smoothScrollToPosition(globalPosition)
                        break@adapter
                    } else {
                        globalPosition++
                    }
                }
            }
        } catch (e: Exception) {
            showToast(e.message)
        }
        return false
    }

    /**
     * 执行输出
     */
    fun executeOutput(): JSONObject {
        val json = JSONObject()
        adapter.adapters.forEach { if (it is FormGroupAdapter) it.executeOutput(json) }
        return json
    }
}
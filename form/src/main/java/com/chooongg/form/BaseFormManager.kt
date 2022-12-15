package com.chooongg.form

import android.content.Context
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.showToast
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.config.FormManagerConfig
import org.json.JSONObject
import java.lang.ref.WeakReference

abstract class BaseFormManager internal constructor(isEditable: Boolean, nameEmsSize: Int) {

    var _recyclerView: WeakReference<RecyclerView>? = null
        protected set

    val adapter = ConcatAdapter(
        ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
    )

    var itemMaxWidth = 0
        private set
    var itemHorizontalSize = 0
        private set
    var itemVerticalSize = 0
        private set
    var itemVerticalEdgeSize = 0
        private set

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
     * 初始化数值
     */
    protected fun initSize(context: Context) {
        itemMaxWidth = context.resDimensionPixelSize(R.dimen.formItemMaxWidth)
        itemHorizontalSize = context.resDimensionPixelSize(R.dimen.formItemHorizontal)
        itemVerticalSize = context.resDimensionPixelSize(R.dimen.formItemVertical)
        itemVerticalEdgeSize = context.resDimensionPixelSize(R.dimen.formItemVerticalEdge)
    }

    /**
     * 全部更新
     */
    fun updateAll(isHasPayloads: Boolean = false) {
        adapter.adapters.forEach {
            if (it is FormGroupAdapter) {
                it.update(isHasPayloads)
            }
        }
    }

    /**
     * 全部删除
     */
    fun clearAll() {
        for (i in adapter.adapters.lastIndex downTo 0) {
            adapter.removeAdapter(adapter.adapters[i])
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

    fun notifyItemChanged(position: Int) {
        adapter.notifyItemChanged(position, "update")
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

    /**
     * 检查数据正确性
     */
    fun checkDataCorrectness(): Boolean {
        try {
            adapter.adapters.forEach {
                if (it is FormGroupAdapter) {
                    it.checkDataCorrectness()
                }
            }
            return true
        } catch (e: FormDataVerificationException) {
            FormManagerConfig.onDataVerificationExceptionBlock.invoke(this, e)
        } catch (e: Exception) {
            showToast("发生异常验证失败")
            e.printStackTrace()
        }
        return false
    }

    /**
     * 执行输出
     */
    fun output(): JSONObject {
        val json = JSONObject()
        adapter.adapters.forEach { if (it is FormGroupAdapter) it.executeOutput(json) }
        return json
    }
}
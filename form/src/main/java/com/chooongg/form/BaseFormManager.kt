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

    /**
     * 通知项目更新
     * @param position 全局坐标
     */
    fun notifyItemChanged(position: Int) {
        adapter.notifyItemChanged(position, "update")
    }

    /**
     * 查找项目并更新
     */
    fun findItem(item: BaseForm, changeBlock: BaseForm.() -> Unit) {
        changeBlock(item)
        group@ for (adapter in adapter.adapters.iterator()) {
            if (adapter !is FormGroupAdapter) continue@group
            val index = adapter.getShowingList().indexOfFirst { it == item }
            if (index != -1) {
                adapter.notifyItemChanged(index, "update")
                return
            }
        }
    }

    /**
     * 根据参数查找项目并更新
     */
    fun findItemForField(field: String, changeBlock: BaseForm.() -> Unit) {
        group@ for (adapter in adapter.adapters.iterator()) {
            if (adapter !is FormGroupAdapter) continue@group
            val index = adapter.getShowingList().indexOfFirst { it.field == field }
            if (index != -1) {
                changeBlock(adapter.getShowingList()[index])
                adapter.notifyItemChanged(index, "update")
                return
            }
        }
    }

    /**
     * 根据名称查找项目并更新
     */
    fun findItemForName(name: CharSequence, changeBlock: BaseForm.() -> Unit) {
        group@ for (adapter in adapter.adapters.iterator()) {
            if (adapter !is FormGroupAdapter) continue@group
            val index = adapter.getShowingList().indexOfFirst { it.name == name }
            if (index != -1) {
                changeBlock(adapter.getShowingList()[index])
                adapter.notifyItemChanged(index, "update")
                return
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
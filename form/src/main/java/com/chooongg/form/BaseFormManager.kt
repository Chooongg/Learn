package com.chooongg.form

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

abstract class BaseFormManager internal constructor(nameEmsSize: Int = 6) {

    internal val adapter = ConcatAdapter(
        ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()
    )

    protected var _recyclerView: WeakReference<RecyclerView>? = null

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

}
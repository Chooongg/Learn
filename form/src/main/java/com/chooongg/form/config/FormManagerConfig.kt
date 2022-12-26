package com.chooongg.form.config

import android.content.Context
import android.view.View
import com.chooongg.basic.APPLICATION
import com.chooongg.basic.ext.resString
import com.chooongg.basic.ext.showToast
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormDataVerificationException
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.R
import com.chooongg.form.bean.FormAddress

object FormManagerConfig {

    var defaultNameEmsSize: Int = 5

    internal var onAddressAreaSelectBlock: (
        context: Context,
        item: FormAddress,
        anchorView: View
    ) -> Unit = { _, _, _ ->
        showToast("未实现")
    }

    internal var onFilePickerBlock: (
        context: Context,
        item: FormAddress,
        anchorView: View
    ) -> Unit = { _, _, _ ->
        showToast("未实现")
    }

    internal var onVerificationDataExceptionBlock: (
        manager: BaseFormManager,
        exception: FormDataVerificationException
    ) -> Unit = { manager, e ->
        if (e.partName.isNullOrEmpty()) showToast(
            APPLICATION.resString(R.string.form_check_data_exception, e.name)
        ) else showToast(
            APPLICATION.resString(R.string.form_check_data_part_exception, e.partName, e.name)
        )

        var globalPosition = 0
        adapter@ for (i in 0 until manager.adapter.adapters.size) {
            if (manager.adapter.adapters[i] !is FormGroupAdapter) {
                globalPosition += manager.adapter.adapters[i].itemCount
                continue@adapter
            }
            val itemAdapter = manager.adapter.adapters[i] as FormGroupAdapter
            for (j in 0 until itemAdapter.itemCount) {
                val item = itemAdapter.getItem(j)
                if (item.field == e.field) {
                    manager.recyclerView?.smoothScrollToPosition(globalPosition)
                    break@adapter
                } else {
                    globalPosition++
                }
            }
        }
    }

    /**
     * 设置地址地区选择监听
     */
    fun setOnAddressAreaSelectListener(
        block: (context: Context, item: FormAddress, anchorView: View) -> Unit
    ) {
        onAddressAreaSelectBlock = block
    }

    /**
     * 设置文件选择监听
     */
    fun setOnFilePickerListener(
        block: (context: Context, item: FormAddress, anchorView: View) -> Unit
    ) {
        onFilePickerBlock = block
    }

    /**
     * 设置验证数据异常操作监听
     */
    fun setOnVerificationDataExceptionListener(
        block: (manager: BaseFormManager, exception: FormDataVerificationException) -> Unit
    ) {
        onVerificationDataExceptionBlock = block
    }
}
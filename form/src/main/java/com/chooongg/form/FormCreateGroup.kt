package com.chooongg.form

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.enum.FormColorStyle

class FormCreateGroup {
    internal val createdFormPartList = mutableListOf<ArrayList<BaseForm>>()

    var groupName: CharSequence? = null

    var groupNameColorStyle: FormColorStyle = FormColorStyle.DEFAULT

    var groupField: String? = null

    @DrawableRes
    var groupIcon: Int? = null

    var groupIconTint: (Context.() -> ColorStateList)? = null

    var dynamicGroup: Boolean = false

    var dynamicMaxPartCount: Int = Int.MAX_VALUE

    internal var dynamicGroupAddPartBlock: (FormCreatePart.() -> Unit)? = null

    internal var dynamicGroupNameFormatBlock: ((name: CharSequence?, index: Int) -> CharSequence)? =
        null

    /**
     * 添加片段
     */
    fun addPart(block: FormCreatePart.() -> Unit) {
        createdFormPartList.add(FormCreatePart().apply(block).createdFormGroupList)
    }

    /**
     * 动态组添加区块
     */
    fun dynamicGroupAddPartListener(block: (FormCreatePart.() -> Unit)?) {
        dynamicGroupAddPartBlock = block
        if (block != null) {
            dynamicGroup = true
        }
    }

    /**
     * 动态组名称格式化
     */
    fun dynamicGroupNameFormatListener(block: ((name: CharSequence?, index: Int) -> CharSequence)?) {
        dynamicGroupNameFormatBlock = block
    }
}
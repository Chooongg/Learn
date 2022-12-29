package com.chooongg.form.creator

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.bean.BaseForm

class FormGroupCreator(val groupName:CharSequence?) {
    internal val createdFormPartList = mutableListOf<ArrayList<BaseForm>>()

    var groupNameColor: (Context.() -> ColorStateList)? = null

    var groupField: String? = null

    @DrawableRes
    var groupIcon: Int? = null

    var groupIconTint: (Context.() -> ColorStateList)? = null

    var dynamicGroup: Boolean = false

    var dynamicGroupDeleteConfirm: Boolean = true

    @androidx.annotation.IntRange(from = 0)
    var dynamicMinPartCount: Int = 1

    @androidx.annotation.IntRange(from = 1)
    var dynamicMaxPartCount: Int = Int.MAX_VALUE

    internal var dynamicGroupAddPartBlock: (FormPartCreator.() -> Unit)? = null

    internal var dynamicGroupNameFormatBlock: ((name: CharSequence?, index: Int) -> CharSequence)? =
        null

    /**
     * 添加片段
     */
    fun addPart(block: FormPartCreator.() -> Unit) {
        createdFormPartList.add(FormPartCreator().apply(block).createdFormGroupList)
    }

    /**
     * 动态组添加区块
     */
    fun dynamicGroupAddPartListener(block: (FormPartCreator.() -> Unit)?) {
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
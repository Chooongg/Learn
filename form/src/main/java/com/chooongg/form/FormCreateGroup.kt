package com.chooongg.form

import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.enum.FormColorStyle

class FormCreateGroup {
    internal val createdFormPartList = mutableListOf<ArrayList<BaseForm>>()

    var groupName:CharSequence? = null

    var groupNameColorStyle: FormColorStyle = FormColorStyle.DEFAULT

    var groupField: String? = null

    @DrawableRes
    var groupIcon: Int? = null

    var groupIconTint: ColorStateList? = null

    /**
     * 添加片段
     */
    fun addPart(block: FormCreatePart.() -> Unit) {
        createdFormPartList.add(FormCreatePart().apply(block).createdFormGroupList)
    }
}
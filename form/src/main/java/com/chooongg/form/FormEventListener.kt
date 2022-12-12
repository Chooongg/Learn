package com.chooongg.form

import android.view.View
import com.chooongg.form.bean.BaseForm

interface FormEventListener {
    /**
     * 表单点击事件
     */
    fun onFormClick(manager: BaseFormManager, item: BaseForm, view: View, position: Int) {}

    /**
     * 表单菜单点击事件
     */
    fun onFormMenuClick(manager: BaseFormManager, item: BaseForm, view: View, position: Int) {}

    /**
     * 表单内容更改事件
     */
    fun onFormContentChanged(manager: BaseFormManager, item: BaseForm, position: Int) {}
}
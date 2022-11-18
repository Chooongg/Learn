package com.chooongg.form

import android.view.View
import com.chooongg.form.bean.BaseForm

interface FormEventListener {
    /**
     * 表单点击事件
     */
    fun onFormClick(manager: FormManager, item: BaseForm, view: View, position: Int) {}

    /**
     * 表单菜单点击事件
     */
    fun onFormMenuClick(manager: FormManager, item: BaseForm, view: View, position: Int) {}

    /**
     * 表单内容更改事件
     */
    fun onFormContentChanged(manager: FormManager, item: BaseForm, position: Int) {}
}
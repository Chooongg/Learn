package com.chooongg.form.style

import android.view.ViewGroup
import com.chooongg.form.FormViewHolder
import com.chooongg.form.bean.BaseForm

class DefaultFormStyle : FormStyle(0) {

    override fun createItemParentView(parent: ViewGroup): ViewGroup? = null

    override fun onBindParentViewHolder(holder: FormViewHolder, item: BaseForm) = Unit
}
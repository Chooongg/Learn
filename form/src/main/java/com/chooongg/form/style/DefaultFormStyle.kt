package com.chooongg.form.style

import android.view.ViewGroup
import com.chooongg.form.FormViewHolder

class DefaultFormStyle : FormStyle() {

    override fun createItemParentView(parent: ViewGroup): ViewGroup? = null

    override fun onBindParentViewHolder(holder: FormViewHolder, boundary: FormBoundary) = Unit
}
package com.chooongg.core.activity

import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getBindingT

abstract class BasicBindingActivity<BINDING : ViewBinding> : BasicActivity() {

    @Suppress("UNCHECKED_CAST")
    protected val binding: BINDING by lazy { getBindingT(getBindingTIndex()) }

    override fun initLayout() = ResourcesCompat.ID_NULL

    override fun setContentViewInternal() {
        setContentView(binding.root)
    }

    protected open fun getBindingTIndex() = 0

}
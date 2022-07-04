package com.chooongg.core.activity

import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getBindingT

abstract class BasicBindingActivity<BINDING : ViewBinding> : BasicActivity() {

    @Suppress("UNCHECKED_CAST")
    protected val binding: BINDING by lazy { getBindingT() }

    override fun setContentViewInternal() {
        setContentView(binding.root)
    }

}
package com.chooongg.core.activity

import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getBindingT

abstract class BasicBindingActivity<BINDING : ViewBinding> : BasicActivity() {

    protected lateinit var binding: BINDING
        private set

    override fun initLayout() = ResourcesCompat.ID_NULL

    override fun setContentViewInternal() {
        setContentView(getBindingT<BINDING>(getBindingTIndex()).apply { binding = this }.root)
    }

    protected open fun getBindingTIndex() = 0

}
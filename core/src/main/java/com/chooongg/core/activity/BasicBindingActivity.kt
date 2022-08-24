package com.chooongg.core.activity

import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getBindingT

abstract class BasicBindingActivity<BINDING : ViewBinding> : BasicActivity() {

    private var _binding: BINDING? = null

    protected val binding: BINDING get() = _binding!!

    override fun initLayout() = ResourcesCompat.ID_NULL

    override fun setContentViewInternal() {
        setContentView(getBindingT<BINDING>(getBindingTIndex()).apply {
            _binding = this
        }.root)
    }

    protected open fun getBindingTIndex() = 0

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
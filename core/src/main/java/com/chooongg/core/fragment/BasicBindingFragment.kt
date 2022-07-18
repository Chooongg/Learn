package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getBindingT

abstract class BasicBindingFragment<BINDING : ViewBinding> : BasicFragment() {

    protected lateinit var binding: BINDING
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getBindingT<BINDING>(inflater, container, false).also {
        binding = it
    }.root
}
package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getBindingT

abstract class BasicBindingFragment<BINDING : ViewBinding> : BasicFragment() {

    private var _binding: BINDING? = null

    protected val binding: BINDING get() = _binding!!

    override fun initLayout() = ResourcesCompat.ID_NULL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getBindingT<BINDING>(getBindingTIndex(), inflater, container, false).also {
        _binding = it
    }.root

    protected open fun getBindingTIndex() = 0

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
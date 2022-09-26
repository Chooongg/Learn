package com.chooongg.core.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import com.chooongg.basic.ext.getTClass

abstract class BasicModelFragment<MODEL : ViewModel> : BasicFragment() {

    @Suppress("UNCHECKED_CAST", "LeakingThis")
    val model: MODEL by ViewModelLazy(
        (javaClass.getTClass(getViewModelTIndex()) as Class<MODEL>).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

    var isHasLoadViewModel = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null){
            isHasLoadViewModel = true
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) initViewModelContent()
    }

    override fun initContentByLazy() {
        if (isHasLoadViewModel) initViewModelContentByLazy()
    }

    open fun initViewModelContent() {}

    open fun initViewModelContentByLazy() {}

    protected open fun getViewModelTIndex() = 0
}
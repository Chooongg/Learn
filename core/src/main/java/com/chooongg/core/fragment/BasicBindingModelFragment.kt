package com.chooongg.core.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.chooongg.basic.ext.getTClass

abstract class BasicBindingModelFragment<BINDING : ViewBinding, MODEL : ViewModel> :
    BasicBindingFragment<BINDING>() {

    @Suppress("UNCHECKED_CAST", "LeakingThis")
    val model: MODEL by ViewModelLazy(
        (javaClass.getTClass(getViewModelTIndex()) as Class<MODEL>).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

    protected open fun getViewModelTIndex() = 1

}
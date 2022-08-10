package com.chooongg.core.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.chooongg.basic.ext.getTClass

abstract class BasicBindingModelActivity<BINDING : ViewBinding, MODEL : ViewModel> :
    BasicBindingActivity<BINDING>() {

    @Suppress("UNCHECKED_CAST")
    val model: MODEL by ViewModelLazy(
        (javaClass.getTClass(1) as Class<MODEL>).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

    protected open fun getViewModelTIndex() = 1
}
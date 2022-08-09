package com.chooongg.core.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getViewModelTClass

abstract class BasicBindingModelFragment<BINDING : ViewBinding, MODEL : ViewModel> :
    BasicBindingFragment<BINDING>() {

    val model: MODEL by ViewModelLazy(
        getViewModelTClass<MODEL>(fragment).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

}
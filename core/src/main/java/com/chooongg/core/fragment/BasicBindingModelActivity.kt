package com.chooongg.core.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getViewModelTClass

abstract class BasicBindingModelActivity<BINDING : ViewBinding, MODEL : ViewModel> :
    BasicBindingFragment<BINDING>() {

    val viewModel: MODEL by ViewModelLazy(
        getViewModelTClass<MODEL>(fragment).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

}
package com.chooongg.core.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.getViewModelTClass

abstract class BasicBindingModelActivity<BINDING : ViewBinding, MODEL : ViewModel> : BasicBindingActivity<BINDING>() {

    val viewModel: MODEL by ViewModelLazy(
        getViewModelTClass<MODEL>(activity).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

}
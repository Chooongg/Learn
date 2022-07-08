package com.chooongg.core.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import com.chooongg.core.ext.getViewModelTClass

abstract class BasicModelActivity<MODEL : ViewModel> : BasicActivity() {

    val model: MODEL by ViewModelLazy(
        getViewModelTClass<MODEL>(activity).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

}
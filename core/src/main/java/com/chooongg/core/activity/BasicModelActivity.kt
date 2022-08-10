package com.chooongg.core.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import com.chooongg.basic.ext.getTClass

abstract class BasicModelActivity<MODEL : ViewModel> : BasicActivity() {
    @Suppress("UNCHECKED_CAST", "LeakingThis")
    val model: MODEL by ViewModelLazy(
        (javaClass.getTClass(getViewModelTIndex()) as Class<MODEL>).kotlin,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { defaultViewModelCreationExtras }
    )

    protected open fun getViewModelTIndex() = 0
}
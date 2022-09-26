package com.chooongg.core.activity

import android.os.Bundle
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

    override fun initContent(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) initViewModelContent()
    }

    open fun initViewModelContent() {}

    protected open fun getViewModelTIndex() = 1
}
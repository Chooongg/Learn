package com.chooongg.core.viewModel

import androidx.lifecycle.ViewModel

class BasicModel : ViewModel() {

    var test: CharSequence? = null

    override fun onCleared() {
        super.onCleared()
    }
}
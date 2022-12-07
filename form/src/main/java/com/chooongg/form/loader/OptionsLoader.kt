package com.chooongg.form.loader

import com.chooongg.form.bean.BaseOptionForm
import com.chooongg.form.bean.Option
import com.chooongg.form.enum.FormOptionsLoadState

abstract class OptionsLoader<T : Option> {

    var state: FormOptionsLoadState = FormOptionsLoadState.WAIT
        private set

    protected abstract suspend fun load(): OptionsLoadResult<T>

    suspend fun loadOptions(item: BaseOptionForm): OptionsLoadResult<T> {
        state = FormOptionsLoadState.LOADING
        val load = load()
        if (load is OptionsLoadResult.Success) {
            state = FormOptionsLoadState.WAIT
            item.options = load.data
        } else if (load is OptionsLoadResult.Error) {
            state = FormOptionsLoadState.ERROR
            item.options = null
        }
        return load
    }
}

sealed class OptionsLoadResult<T : Option> protected constructor() {
    data class Success<T : Option>(val data: List<T>) : OptionsLoadResult<T>()
    data class Error<T : Option>(val throwable: Throwable) : OptionsLoadResult<T>()
}
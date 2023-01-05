package com.chooongg.net

import com.chooongg.basic.ext.withMain
import com.chooongg.net.exception.HttpException

/**
 * 网络请求且返回提封装 DSL
 */
open class CoroutinesRequestDSL<RESPONSE : ResponseData<DATA>, DATA> :
    CoroutinesRequestBasicDSL<RESPONSE>() {

    private var onSuccessMessage: (suspend (String?) -> Unit)? = null
    private var onSuccess: (suspend (DATA) -> Unit)? = null

    fun onSuccessMessage(block: suspend (String?) -> Unit) {
        onSuccessMessage = block
    }

    fun onSuccess(block: suspend (DATA) -> Unit) {
        onSuccess = block
    }

    override suspend fun processData(response: RESPONSE) {
        val data = response.checkData()
        withMain<Unit> {
            onSuccessMessage?.invoke(response.getMessage())
            if (data != null) onSuccess?.invoke(data)
            else if (onSuccessMessage == null) throw HttpException(HttpException.Type.EMPTY)
        }
    }
}
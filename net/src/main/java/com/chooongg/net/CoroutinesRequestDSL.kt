package com.chooongg.net

import com.chooongg.basic.ext.withMain

/**
 * 网络请求且返回提封装 DSL
 */
open class CoroutinesRequestDSL<RESPONSE : ResponseData<DATA?>, DATA> :
    CoroutinesRequestBasicDSL<RESPONSE>() {
    private var onSuccessMessage: (suspend (String?) -> Unit)? = null
    private var onSuccess: (suspend (DATA?) -> Unit)? = null

    fun onSuccessMessage(block: suspend (String?) -> Unit) {
        onSuccessMessage = block
    }

    fun onSuccess(block: suspend (DATA?) -> Unit) {
        onSuccess = block
    }

    override suspend fun processData(response: RESPONSE) {
        val data = response.checkData()
        withMain {
            onSuccessMessage?.invoke(response.getMessage())
            onSuccess?.invoke(data)
        }
    }
}
package com.chooongg.net

import com.chooongg.basic.ext.withIO
import com.chooongg.net.exception.HttpException

/**
 * 网络同步请求且返回提封装 DSL
 */
open class CoroutinesRequestSyncDSL<RESPONSE : ResponseData<DATA?>, DATA> {
    private var api: (suspend () -> RESPONSE)? = null

    fun api(block: suspend () -> RESPONSE) {
        api = block
    }

    protected open suspend fun processData(response: RESPONSE): DATA? = response.checkData()

    internal suspend fun executeRequest(): DATA? {
        if (api == null) {
            throw NullPointerException("HttpRequest not implemented api method, cancel operation.")
        }
        return withIO {
            try {
                processData(api!!.invoke())
            } catch (e: Exception) {
                throw HttpException(e)
            }
        }
    }
}
package com.chooongg.net

import com.chooongg.basic.ext.logE
import com.chooongg.basic.ext.withIO
import com.chooongg.basic.ext.withMain
import com.chooongg.net.exception.HttpException
import com.chooongg.net.exception.HttpRetryException

/**
 * 网络请求 DSL
 */
open class CoroutinesRequestBasicDSL<RESPONSE> {
    private var api: (suspend () -> RESPONSE)? = null
    private var onStart: (suspend () -> Unit)? = null
    private var onResponse: (suspend (RESPONSE) -> Unit)? = null
    private var onError: (suspend (HttpException) -> Unit)? = null
    private var onEnd: (suspend (Boolean) -> Unit)? = null

    fun api(block: suspend () -> RESPONSE) {
        api = block
    }

    fun onResponse(block: suspend (RESPONSE) -> Unit) {
        onResponse = block
    }

    fun onError(block: suspend (HttpException) -> Unit) {
        onError = block
    }

    fun onEnd(block: suspend (Boolean) -> Unit) {
        onEnd = block
    }

    protected open suspend fun processData(response: RESPONSE) = Unit

    suspend fun reExecuteRequest() {
        executeRequest()
    }

    internal suspend fun executeRequest() {
        if (api == null) {
            logE("HttpRequest not implemented api method, cancel operation.")
            return
        }
        withMain { onStart?.invoke() }
        withIO {
            try {
                val response = api!!.invoke()
                withMain { onResponse?.invoke(response) }
                processData(response)
                withMain { onEnd?.invoke(true) }
            } catch (e: HttpRetryException) {
                executeRequest()
            } catch (e: Exception) {
                withMain {
                    onError?.invoke(HttpException(e))
                    onEnd?.invoke(false)
                }
            }
        }
    }
}
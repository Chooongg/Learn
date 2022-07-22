package com.chooongg.net

import com.chooongg.basic.ext.withIO
import com.chooongg.net.exception.HttpException
import com.chooongg.net.exception.HttpRetryException

/**
 * 网络同步请求 DSL
 */
open class CoroutinesRequestSyncBasicDSL<RESPONSE> {
    private var api: (suspend () -> RESPONSE)? = null

    fun api(block: suspend () -> RESPONSE) {
        api = block
    }

    internal suspend fun executeRequest(): RESPONSE {
        if (api == null) {
            throw NullPointerException("HttpRequest not implemented api method, cancel operation.")
        }
        return withIO {
            try {
                api!!.invoke()
            } catch (e: HttpRetryException) {
                executeRequest()
            } catch (e: Exception) {
                throw HttpException(e)
            }
        }
    }
}
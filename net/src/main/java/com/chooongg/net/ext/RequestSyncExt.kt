package com.chooongg.net.ext

import com.chooongg.net.CoroutinesRequestSyncBasicDSL
import com.chooongg.net.CoroutinesRequestSyncDSL
import com.chooongg.net.ResponseData

/**
 * 常规同步请求 DSL
 */
suspend fun <DATA> requestSync(block: suspend CoroutinesRequestSyncDSL<out ResponseData<DATA?>, DATA>.() -> Unit) =
    CoroutinesRequestSyncDSL<ResponseData<DATA?>, DATA>().apply { block(this) }.executeRequest()

/**
 * 基础的同步请求 DSL
 */
suspend fun <RESPONSE> requestBasicSync(block: suspend CoroutinesRequestSyncBasicDSL<RESPONSE>.() -> Unit) =
    CoroutinesRequestSyncBasicDSL<RESPONSE>().apply { block(this) }.executeRequest()
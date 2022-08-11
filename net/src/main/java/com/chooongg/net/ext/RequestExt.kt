package com.chooongg.net.ext

import com.chooongg.net.CoroutinesRequestBasicDSL
import com.chooongg.net.CoroutinesRequestDSL
import com.chooongg.net.ResponseData

/**
 * 常规请求 DSL
 */
suspend fun <DATA> request(block: CoroutinesRequestDSL<ResponseData<DATA>, DATA>.() -> Unit) =
    CoroutinesRequestDSL<ResponseData<DATA>, DATA>().apply { block(this) }.executeRequest()

/**
 * 基础的请求 DSL
 */
suspend fun <RESPONSE> requestBasic(block: CoroutinesRequestBasicDSL<RESPONSE>.() -> Unit) =
    CoroutinesRequestBasicDSL<RESPONSE>().apply { block(this) }.executeRequest()

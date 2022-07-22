package com.chooongg.net.ext

import com.chooongg.net.CoroutinesRequestBasicDSL
import com.chooongg.net.CoroutinesRequestDSL
import com.chooongg.net.ResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 启动返回体封装的请求
 */
fun <DATA> CoroutineScope.launchRequest(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: CoroutinesRequestDSL<out ResponseData<DATA?>, DATA>.() -> Unit
) = launch(context, start) { request(block) }


/**
 * 启动请求
 */
fun <RESPONSE> CoroutineScope.launchRequestBasic(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: CoroutinesRequestBasicDSL<RESPONSE>.() -> Unit
) = launch(context, start) { requestBasic(block) }

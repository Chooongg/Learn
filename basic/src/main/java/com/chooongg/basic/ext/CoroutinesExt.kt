package com.chooongg.basic.ext

import kotlinx.coroutines.*

val globeScope get() = CoroutineScope(Job() + Dispatchers.Default)

suspend inline fun <T> withDefault(noinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default, block)

suspend inline fun <T> withMain(noinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main, block)

suspend inline fun <T> withUnconfined(noinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Unconfined, block)

suspend inline fun <T> withIO(noinline block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO, block)

fun CoroutineScope.launchDefault(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = launch(Dispatchers.Default, start, block)

fun CoroutineScope.launchMain(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = launch(Dispatchers.Main, start, block)

fun CoroutineScope.launchUnconfined(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = launch(Dispatchers.Unconfined, start, block)

fun CoroutineScope.launchIO(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = launch(Dispatchers.IO, start, block)
package com.chooongg.eventflow

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * 全局范围发送事件
 */
inline fun <reified T : Any> postEvent(
    event: T, timeMillis: Long = 0L
) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventFlowCore::class.java)
        .postEvent(T::class.java.name, event, timeMillis)
}

/**
 * 全局范围发送事件标签
 */
fun postEventTag(tag: String, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventFlowCore::class.java)
        .postEventTag(tag, timeMillis)
}

/**
 * 限定范围发送事件
 */
inline fun <reified T : Any> postEvent(
    scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L
) {
    ViewModelProvider(scope)[EventFlowCore::class.java]
        .postEvent(T::class.java.name, event, timeMillis)
}

/**
 * 限定范围内发送事件标签
 */
fun postEventTag(scope: ViewModelStoreOwner, tag: String, timeMillis: Long = 0L) {
    ViewModelProvider(scope)[EventFlowCore::class.java].postEventTag(tag, timeMillis)
}
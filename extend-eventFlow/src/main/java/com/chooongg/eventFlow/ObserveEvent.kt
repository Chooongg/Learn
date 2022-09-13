package com.chooongg.eventFlow

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 监听全局范围事件
 */
@MainThread
inline fun <reified T> LifecycleOwner.observeEvent(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) = ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventFlowCore::class.java)
    .observeEvent(this, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)

/**
 * 监听全局范围事件标签
 */
@MainThread
fun LifecycleOwner.observeEventTag(
    tag: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    isSticky: Boolean = false,
    onReceived: () -> Unit
) = ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventFlowCore::class.java)
    .observeEventTag(this, tag, minActiveState, dispatcher, isSticky, onReceived)

/**
 * 监听Fragment范围事件
 */
@MainThread
inline fun <reified T> observeEvent(
    scope: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) = ViewModelProvider(scope)[EventFlowCore::class.java]
    .observeEvent(scope, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)

/**
 * 监听Fragment范围事件标签
 */
@MainThread
fun observeEventTag(
    scope: Fragment,
    tag: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    isSticky: Boolean = false,
    onReceived: () -> Unit
) = ViewModelProvider(scope)[EventFlowCore::class.java]
    .observeEventTag(scope, tag, minActiveState, dispatcher, isSticky, onReceived)

/**
 * 监听Activity范围事件
 */
@MainThread
inline fun <reified T> observeEvent(
    scope: ComponentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) = ViewModelProvider(scope)[EventFlowCore::class.java]
    .observeEvent(scope, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)

/**
 * 监听Activity范围事件标签
 */
@MainThread
fun observeEventTag(
    scope: ComponentActivity,
    tag: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    isSticky: Boolean = false,
    onReceived: () -> Unit
) = ViewModelProvider(scope)[EventFlowCore::class.java]
    .observeEventTag(scope, tag, minActiveState, dispatcher, isSticky, onReceived)

/**
 * 监听全局范围事件 (无视生命周期)
 */
@MainThread
inline fun <reified T> observeEvent(
    coroutineScope: CoroutineScope,
    isSticky: Boolean,
    noinline onReceived: (T) -> Unit
) = coroutineScope.launch {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventFlowCore::class.java)
        .observerWithoutLifecycle(T::class.java.name, isSticky, onReceived)
}

/**
 * 监听全局范围事件标签 (无视生命周期)
 */
@MainThread
fun observeEventTag(
    coroutineScope: CoroutineScope,
    tag: String,
    isSticky: Boolean,
    onReceived: () -> Unit
) = coroutineScope.launch {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventFlowCore::class.java)
        .observerTagWithoutLifecycle(tag, isSticky, onReceived)
}

/**
 * 监听范围事件 (无视生命周期)
 */
@MainThread
inline fun <reified T> observeEvent(
    scope: ViewModelStoreOwner,
    coroutineScope: CoroutineScope,
    isSticky: Boolean,
    noinline onReceived: (T) -> Unit
) = coroutineScope.launch {
    ViewModelProvider(scope)[EventFlowCore::class.java]
        .observerWithoutLifecycle(T::class.java.name, isSticky, onReceived)
}

/**
 * 监听范围事件标签 (无视生命周期)
 */
@MainThread
fun observeEventTag(
    scope: ViewModelStoreOwner,
    coroutineScope: CoroutineScope,
    tag: String,
    isSticky: Boolean,
    onReceived: () -> Unit
) = coroutineScope.launch {
    ViewModelProvider(scope)[EventFlowCore::class.java]
        .observerTagWithoutLifecycle(tag, isSticky, onReceived)
}
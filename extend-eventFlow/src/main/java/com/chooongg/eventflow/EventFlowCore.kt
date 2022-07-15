package com.chooongg.eventflow

import androidx.lifecycle.*
import com.chooongg.basic.ext.logE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

class EventFlowCore : ViewModel() {

    private val eventFlows = HashMap<String, MutableSharedFlow<Any>>()

    private val stickyEventFlows = HashMap<String, MutableSharedFlow<Any>>()

    private fun getEventFlow(eventName: String, isSticky: Boolean): MutableSharedFlow<Any> =
        if (isSticky) {
            stickyEventFlows[eventName]
        } else {
            eventFlows[eventName]
        } ?: MutableSharedFlow<Any>(
            if (isSticky) 1 else 0,
            Int.MAX_VALUE
        ).also {
            if (isSticky) {
                stickyEventFlows[eventName] = it
            } else {
                eventFlows[eventName] = it
            }
        }

    fun <T : Any> observeEvent(
        lifecycleOwner: LifecycleOwner,
        eventName: String,
        minState: Lifecycle.State,
        dispatcher: CoroutineDispatcher,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ): Job = lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.whenStateAtLeast(minState) {
            getEventFlow(eventName, isSticky).collect {
                this.launch(dispatcher) {
                    invokeReceived(it, onReceived)
                }
            }
        }
    }

    suspend fun <T : Any> observerWithoutLifecycle(
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        getEventFlow(eventName, isSticky).collect { invokeReceived(it, onReceived) }
    }

    fun postEvent(eventName: String, value: Any, timeMillis: Long) {
        listOfNotNull(
            getEventFlow(eventName, false),
            getEventFlow(eventName, true)
        ).forEach {
            viewModelScope.launch {
                delay(timeMillis)
                it.emit(value)
            }
        }
    }

    fun removeStickEvent(eventName: String) {
        stickyEventFlows.remove(eventName)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clearStickEvent(eventName: String) {
        stickyEventFlows[eventName]?.resetReplayCache()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> invokeReceived(value: Any, onReceived: (T) -> Unit) {
        try {
            onReceived(value as T)
        } catch (e: ClassCastException) {
            logE("class cast error on message received: $value", e)
        } catch (e: Exception) {
            logE("error on message received: $value", e)
        }
    }

    fun getEventObserverCount(eventName: String): Int {
        val stickyObserverCount = stickyEventFlows[eventName]?.subscriptionCount?.value ?: 0
        val normalObserverCount = eventFlows[eventName]?.subscriptionCount?.value ?: 0
        return stickyObserverCount + normalObserverCount
    }
}
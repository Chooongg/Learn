package com.chooongg.net

abstract class ResponseData<T> {
    abstract fun getCode(): String?
    abstract fun getMessage(): String?
    abstract fun getData(): T?

    /**
     * 验证是否成功
     */
    abstract fun validation(): Boolean
}
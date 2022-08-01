package com.chooongg.learn.api

import com.chooongg.net.ResponseData

data class ResponseWanAndroidAPI<T>(
    private val code: Int,
    private val message: String,
    private val data: T?
) : ResponseData<T>() {
    override fun getCode() = code.toString()
    override fun getMessage() = message
    override suspend fun checkData(): T? {
        return data
    }
}
package com.chooongg.learn.api

import com.chooongg.net.ResponseData
import com.chooongg.net.exception.HttpException

data class ResponseWanAndroidAPI<T>(
    private val errorCode: Int?,
    private val errorMsg: String?,
    private val data: T?
) : ResponseData<T>() {
    override fun getCode() = errorCode.toString()
    override fun getMessage() = errorMsg
    override suspend fun checkData(): T? {
        if (errorCode == 0) return data
        if (errorMsg != null) throw HttpException(errorMsg) else throw HttpException()
    }
}
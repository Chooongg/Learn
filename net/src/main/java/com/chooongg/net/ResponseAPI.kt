package com.chooongg.net

data class ResponseAPI<T>(
    private val code: Int,
    private val message: String,
    private val data: T?
) : ResponseData<T>() {
    override fun getCode() = code.toString()
    override fun getMessage() = message
    override fun getData() = data
}
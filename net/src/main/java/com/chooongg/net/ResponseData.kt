package com.chooongg.net

abstract class ResponseData<DATA> {

    /**
     * 获取 Code
     */
    abstract fun getCode(): String?

    /**
     * 获取 Message
     */
    abstract fun getMessage(): String?

    /**
     * 检查 Data
     * 如果验证失败，请直接抛出异常
     * 如果想重新请求，请抛出[com.chooongg.net.exception.HttpRetryException]异常
     * @return 返回 Data 数据
     */
    abstract suspend fun checkData(): DATA?
}
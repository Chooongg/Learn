package com.chooongg.learn.api

import com.chooongg.basic.ext.withMain
import com.chooongg.net.BaseUrl
import com.chooongg.net.RetrofitManager
import retrofit2.http.GET

@BaseUrl("https://wanandroid.com/maven_pom/package/")
interface WanAndroidAPI {

    companion object {
        val service by lazy { RetrofitManager.getAPI(WanAndroidAPI::class) }
    }

    @GET("json")
    suspend fun getPackageList(): ResponseWanAndroidAPI<Any>
}
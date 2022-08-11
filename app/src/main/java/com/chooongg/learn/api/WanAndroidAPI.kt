package com.chooongg.learn.api

import com.chooongg.net.RetrofitManager
import retrofit2.http.GET

interface WanAndroidAPI {

    companion object {
        val service = RetrofitManager.Builder(WanAndroidAPI::class)
            .baseUrl("https://wanandroid.com/maven_pom/package/")
            .build()
    }

    @GET("json")
    suspend fun getPackageList(): ResponseWanAndroidAPI<MutableList<String>>
}
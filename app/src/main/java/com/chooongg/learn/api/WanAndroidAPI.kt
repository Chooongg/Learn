package com.chooongg.learn.api

import com.chooongg.core.adapter.BindingAdapter
import com.chooongg.net.RetrofitManager
import retrofit2.http.GET

interface WanAndroidAPI {

    companion object {
        val service by lazy {
            RetrofitManager.Builder<WanAndroidAPI>()
                .baseUrl("https://wanandroid.com/maven_pom/package/")
                .build()
        }
    }

    @GET("json")
    suspend fun getPackageList(): ResponseWanAndroidAPI<String>
}
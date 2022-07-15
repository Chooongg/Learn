package com.chooongg.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

object RetrofitManager {

    var connectTimeout: Long = 10
    var writeTimeout: Long = 10
    var readTimeout: Long = 10

    fun <T : Any> getAPI(
        clazz: KClass<T>,
        clientBlock: (OkHttpClient.Builder.() -> Unit)? = null,
        retrofitBlock: (Retrofit.Builder.() -> Unit)? = null
    ): T = getAPI(
        clazz.java.getAnnotation(BaseUrl::class.java)?.value,
        clazz, clientBlock, retrofitBlock
    )

    fun <T : Any> getAPI(
        baseUrl: String?,
        clazz: KClass<T>,
        clientBlock: (OkHttpClient.Builder.() -> Unit)? = null,
        retrofitBlock: (Retrofit.Builder.() -> Unit)? = null
    ): T = Retrofit.Builder().also {
        if (!baseUrl.isNullOrEmpty()) it.baseUrl(baseUrl)
        it.client(
            OkHttpClient.Builder().also { client ->
                client.connectTimeout(connectTimeout, TimeUnit.SECONDS)
                client.writeTimeout(writeTimeout, TimeUnit.SECONDS)
                client.readTimeout(readTimeout, TimeUnit.SECONDS)
                clientBlock?.invoke(client)
            }.build()
        )
        retrofitBlock?.invoke(it)
    }.build().create(clazz.java)
}
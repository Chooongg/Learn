package com.chooongg.net

import com.chooongg.basic.APPLICATION
import com.chooongg.basic.ext.getTClass
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import kotlin.reflect.KClass

object RetrofitManager {

    const val DEFAULT_TIMEOUT = 30L // 默认超时时间 秒为单位
    const val DEFAULT_HTTP_CACHE_SIZE = 10L * 1048576L // 默认缓存大小

    class Builder<T> {

        private var retrofitBuilder = Retrofit.Builder()
        private var okhttpBuilder = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(Cache(File(APPLICATION.cacheDir, "okHttpCache"), DEFAULT_HTTP_CACHE_SIZE))

        private var baseUrl: HttpUrl? = null

        // 缓存保存时间-秒
        private var maxAge: Long = 60L * 60 * 24 * 7

        // 缓存保存时间-秒
        private var maxStale: Long = 60L * 60 * 24 * 7

        private var retrofitBlock: (Retrofit.Builder.() -> Unit)? = null
        private var okHttpClientBlock: (OkHttpClient.Builder.() -> Unit)? = null

        fun baseUrl(baseUrl: URL) =
            apply { retrofitBuilder.baseUrl(baseUrl) }

        fun baseUrl(baseUrl: String) =
            apply { retrofitBuilder.baseUrl(baseUrl) }

        fun baseUrl(baseUrl: HttpUrl) =
            apply { retrofitBuilder.baseUrl(baseUrl) }

        fun addConverterFactory(factory: Converter.Factory) =
            apply { retrofitBuilder.addConverterFactory(factory) }

        fun clearConverterFactory() =
            apply { retrofitBuilder.converterFactories().clear() }

        fun addCallAdapterFactory(factory: CallAdapter.Factory) =
            apply { retrofitBuilder.addCallAdapterFactory(factory) }

        fun clearCallAdapterFactory() =
            apply { retrofitBuilder.callAdapterFactories().clear() }


        fun connectTimeout(timeout: Long, unit: TimeUnit) =
            apply { okhttpBuilder.connectTimeout(timeout, unit) }

        fun writeTimeout(timeout: Long, unit: TimeUnit) =
            apply { okhttpBuilder.writeTimeout(timeout, unit) }

        fun readTimeout(timeout: Long, unit: TimeUnit) =
            apply { okhttpBuilder.readTimeout(timeout, unit) }

        fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean) =
            apply { okhttpBuilder.retryOnConnectionFailure(retryOnConnectionFailure) }

        fun sslSocketFactory(sslSocketFactory: SSLSocketFactory, trustManager: X509TrustManager) =
            apply { okhttpBuilder.sslSocketFactory(sslSocketFactory, trustManager) }

        fun addInterceptor(interceptor: Interceptor) =
            apply { okhttpBuilder.addInterceptor(interceptor) }

        fun clearInterceptor() =
            apply { okhttpBuilder.interceptors().clear() }

        fun addNetworkInterceptor(interceptor: Interceptor) =
            apply { okhttpBuilder.addNetworkInterceptor(interceptor) }

        fun clearNetworkInterceptor() =
            apply { okhttpBuilder.networkInterceptors().clear() }


        fun retrofitExtend(value: Retrofit.Builder.() -> Unit) =
            apply { retrofitBlock = value }

        fun okHttpExtend(value: OkHttpClient.Builder.() -> Unit) =
            apply { okHttpClientBlock = value }

        /**
         * 浏览器调试地址：
         * edge   -> edge://inspect/#devices
         * chrome -> chrome://inspect/#devices
         */
        @Suppress("UNCHECKED_CAST")
        fun build(): T {
            okHttpClientBlock?.invoke(okhttpBuilder)
            okhttpBuilder.addNetworkInterceptor(StethoInterceptor())
            retrofitBuilder.client(okhttpBuilder.build())
            if (baseUrl != null) retrofitBuilder.baseUrl(baseUrl!!)
            retrofitBlock?.invoke(retrofitBuilder)
            val retrofit = retrofitBuilder.build()
            return retrofit.create(javaClass.getTClass()) as T
        }
    }
}
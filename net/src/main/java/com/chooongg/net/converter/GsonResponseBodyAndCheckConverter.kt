package com.chooongg.net.converter

import androidx.annotation.MainThread
import com.chooongg.basic.ext.getTClass
import com.chooongg.basic.ext.withMain
import com.chooongg.net.ResponseData
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import kotlin.Throws
import com.google.gson.JsonIOException
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Converter
import java.io.IOException

internal class GsonResponseBodyAndCheckConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val jsonReader = gson.newJsonReader(value.charStream())
        return value.use {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            if (result is ResponseData<*>){
                result.validation()
            }
            result
        }
    }
}
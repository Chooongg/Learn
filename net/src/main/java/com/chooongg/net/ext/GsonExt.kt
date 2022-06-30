package com.chooongg.net.ext

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.hjq.gson.factory.GsonFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

val gson: Gson get() = GsonFactory.getSingletonGson()

fun JSONObject.toRequestBody() =
    toString().toRequestBody("application/json; charset=utf-8".toMediaType())

fun JSONArray.toRequestBody() =
    toString().toRequestBody("application/json; charset=utf-8".toMediaType())

fun JsonElement.toRequestBody() =
    toString().toRequestBody("application/json; charset=utf-8".toMediaType())

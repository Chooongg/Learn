package com.chooongg.basic.ext

import android.annotation.SuppressLint
import android.util.Log

private fun getTag(offset: Int): String {
    val stackTrace = Throwable().stackTrace
    val caller = when {
        3 + offset < 0 -> stackTrace[0]
        3 + offset > stackTrace.lastIndex -> stackTrace[stackTrace.lastIndex]
        else -> stackTrace[3 + offset]
    }
    return String.format("(%s:%d)", caller.fileName, caller.lineNumber)
}

private fun getClassTag(clazz: Class<*>): String {
    return if (clazz.isAnnotationPresent(Metadata::class.java)) {
        String.format("(%s.kt:%d)", clazz.simpleName, 0)
    } else String.format("(%s.java:%d)", clazz.simpleName, 0)
}

fun logV(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.v(getTag(offsetStack), msg)
        else Log.v(getTag(offsetStack), msg, e)
    }
}

fun logVTag(tag: String, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.v(tag, msg) else Log.v(tag, msg, e)
}

fun logVClass(clazz: Class<*>, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.v(getClassTag(clazz), msg)
    else Log.v(getClassTag(clazz), msg, e)
}

fun logD(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.d(getTag(offsetStack), msg)
        else Log.d(getTag(offsetStack), msg, e)
    }
}

fun logDTag(tag: String, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.d(tag, msg) else Log.d(tag, msg, e)
}

fun logDClass(clazz: Class<*>, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.d(getClassTag(clazz), msg)
    else Log.d(getClassTag(clazz), msg, e)
}

fun logI(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.i(getTag(offsetStack), msg)
        else Log.i(getTag(offsetStack), msg, e)
    }
}

fun logITag(tag: String, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.i(tag, msg) else Log.i(tag, msg, e)
}

fun logIClass(clazz: Class<*>, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.i(getClassTag(clazz), msg)
    else Log.i(getClassTag(clazz), msg, e)
}

fun logW(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.w(getTag(offsetStack), msg)
        else Log.w(getTag(offsetStack), msg, e)
    }
}

fun logWTag(tag: String, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.w(tag, msg) else Log.w(tag, msg, e)
}

fun logWClass(clazz: Class<*>, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.w(getClassTag(clazz), msg)
    else Log.w(getClassTag(clazz), msg, e)
}

fun logE(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.e(getTag(offsetStack), msg)
        else Log.e(getTag(offsetStack), msg, e)

    }
}

fun logETag(tag: String, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.e(tag, msg) else Log.e(tag, msg, e)
}

fun logEClass(clazz: Class<*>, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.e(getClassTag(clazz), msg)
    else Log.e(getClassTag(clazz), msg, e)
}

@SuppressLint("NewApi")
fun logWTF(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.wtf(getTag(offsetStack), msg)
        else Log.wtf(getTag(offsetStack), msg, e)

    }
}

@SuppressLint("NewApi")
fun logWTFTag(tag: String, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.wtf(tag, msg) else Log.wtf(tag, msg, e)
}

@SuppressLint("NewApi")
fun logWTFClass(clazz: Class<*>, msg: String, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.wtf(getClassTag(clazz), msg)
    else Log.wtf(getClassTag(clazz), msg, e)
}

object BasicLog {
    internal var isEnable = isAppDebug()

    fun setEnable(enable: Boolean) {
        isEnable = enable
    }
}
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
    return String.format("%s(%s:%d) ", caller.methodName, caller.fileName, caller.lineNumber)
}

private fun getClassTag(clazz: Class<*>): String {
    return if (clazz.isAnnotationPresent(Metadata::class.java)) {
        String.format("(%s.kt:%d) ", clazz.simpleName, 0)
    } else String.format("(%s.java:%d) ", clazz.simpleName, 0)
}

fun logV(tag: String, msg: Any?, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.v(tag, "${getTag(offsetStack)}${msg}")
        else Log.v(tag, "${getTag(offsetStack)}${msg}", e)
    }
}

fun logVClass(tag: String, clazz: Class<*>, msg: Any?, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.v(tag, "${getClassTag(clazz)}${msg}")
    else Log.v(tag, "${getClassTag(clazz)}${msg}", e)
}

fun logD(tag: String, msg: Any?, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.d(tag, "${getTag(offsetStack)}${msg}")
        else Log.d(tag, "${getTag(offsetStack)}${msg}", e)
    }
}

fun logDClass(tag: String, clazz: Class<*>, msg: Any?, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.d(tag, "${getClassTag(clazz)}${msg}")
    else Log.d(tag, "${getClassTag(clazz)}${msg}", e)
}

fun logI(tag: String, msg: Any?, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.i(tag, "${getTag(offsetStack)}${msg}")
        else Log.i(tag, "${getTag(offsetStack)}${msg}", e)
    }
}

fun logIClass(tag: String, clazz: Class<*>, msg: Any?, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.i(tag, "${getClassTag(clazz)}${msg}")
    else Log.i(tag, "${getClassTag(clazz)}${msg}", e)
}

fun logW(tag: String, msg: Any?, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.w(tag, "${getTag(offsetStack)}${msg}")
        else Log.w(tag, "${getTag(offsetStack)}${msg}", e)
    }
}

fun logWClass(tag: String, clazz: Class<*>, msg: Any?, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.w(tag, "${getClassTag(clazz)}${msg}")
    else Log.w(tag, "${getClassTag(clazz)}${msg}", e)
}

fun logE(tag: String, msg: Any?, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.e(tag, "${getTag(offsetStack)}${msg}")
        else Log.e(tag, "${getTag(offsetStack)}${msg}", e)
    }
}

fun logEClass(tag: String, clazz: Class<*>, msg: Any?, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.e(tag, "${getClassTag(clazz)}${msg}")
    else Log.e(tag, "${getClassTag(clazz)}${msg}", e)
}

@SuppressLint("NewApi")
fun logWTF(tag: String, msg: Any?, e: Throwable? = null, offsetStack: Int = 0) {
    if (BasicLog.isEnable) {
        if (e == null) Log.wtf(tag, "${getTag(offsetStack)}${msg}")
        else Log.wtf(tag, "${getTag(offsetStack)}${msg}", e)
    }
}

@SuppressLint("NewApi")
fun logWTFClass(tag: String, clazz: Class<*>, msg: Any?, e: Throwable? = null) {
    if (BasicLog.isEnable) if (e == null) Log.wtf(tag, "${getClassTag(clazz)}${msg}")
    else Log.wtf(tag, "${getClassTag(clazz)}${msg}", e)
}

object BasicLog {
    internal var isEnable = isAppDebug()

    fun setEnable(enable: Boolean) {
        isEnable = enable
    }
}
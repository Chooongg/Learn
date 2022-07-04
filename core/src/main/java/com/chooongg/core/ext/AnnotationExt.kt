package com.chooongg.core.ext

import android.content.Context
import com.chooongg.core.annotation.Title
import com.chooongg.core.annotation.TitleId

fun Class<*>.getAnnotationTitle(context: Context?): CharSequence? {
    getAnnotation(Title::class.java)?.let {
        return it.value
    }
    getAnnotation(TitleId::class.java)?.let {
        return context?.getString(it.value)
    }
    return null
}
package com.chooongg.adapter

import android.content.Context
import android.view.LayoutInflater
import java.lang.reflect.ParameterizedType

internal object AdapterUtils {

    @Suppress("UNCHECKED_CAST")
    fun <T> getBinding(currentClass: Class<*>, context: Context, index: Int): T {
        val clazz = currentClass.getTClass(index)
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, LayoutInflater.from(context)) as T
    }

    fun Class<*>.getTClass(index: Int): Class<*> {
        var genericSuperclass = genericSuperclass
        var superclass = superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                return genericSuperclass.actualTypeArguments[index] as Class<*>
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        throw NullPointerException("没有找到泛型T")
    }
}
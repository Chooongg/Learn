package com.chooongg.basic.ext

import java.lang.reflect.ParameterizedType

/**
 * 获取类的第 N 项泛型
 * @param index 泛型指数
 * @param targetClass 目标类
 */
fun Class<*>.getTClass(index: Int = 0): Class<*> {
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
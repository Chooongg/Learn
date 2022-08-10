package com.chooongg.core.ext

import androidx.lifecycle.ViewModel
import com.chooongg.basic.LearnFrameException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
fun <MODEL : ViewModel> getViewModelClass(any: Any): Class<MODEL> {
    var genericSuperclass = any.javaClass.genericSuperclass
    var superclass = any.javaClass.superclass
    while (superclass != null) {
        if (genericSuperclass is ParameterizedType) {
            genericSuperclass.actualTypeArguments.forEach {
                try {
                    return it as Class<MODEL>
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
            }
        }
        genericSuperclass = superclass.genericSuperclass
        superclass = superclass.superclass
    }
    throw LearnFrameException("没有找到ViewModel泛型")
}
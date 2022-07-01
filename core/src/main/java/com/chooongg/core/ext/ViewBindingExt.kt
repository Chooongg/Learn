package com.chooongg.core.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.chooongg.basic.LearnFrameException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
fun <BINDING : ViewBinding> AppCompatActivity.inflateBinding(): BINDING {
    val clazz = bindingClass<BINDING>(this)
    val method = clazz.getMethod("inflate", LayoutInflater::class.java)
    val binding = method.invoke(null, LayoutInflater.from(this)) as BINDING
    if (binding is ViewDataBinding) (binding as ViewDataBinding).lifecycleOwner = this
    return binding
}

@Suppress("UNCHECKED_CAST")
fun <BINDING : ViewBinding> Fragment.inflateBinding(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
): BINDING {
    val clazz = bindingClass<BINDING>(this)
    val binding = clazz.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, layoutInflater, parent, attachToParent) as BINDING
    if (binding is ViewDataBinding) (binding as ViewDataBinding).lifecycleOwner = viewLifecycleOwner
    return binding
}

@Suppress("UNCHECKED_CAST")
private fun <BINDING : ViewBinding> bindingClass(any: Any): Class<BINDING> {
    var genericSuperclass = any.javaClass.genericSuperclass
    var superclass = any.javaClass.superclass
    while (superclass != null) {
        if (genericSuperclass is ParameterizedType) {
            genericSuperclass.actualTypeArguments.forEach {
                try {
                    return it as Class<BINDING>
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
    throw LearnFrameException("没有找到ViewBinding泛型")
}
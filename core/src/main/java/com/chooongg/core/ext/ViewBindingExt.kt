package com.chooongg.core.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.chooongg.basic.ext.getTClass

@Suppress("UNCHECKED_CAST")
fun <BINDING : ViewBinding> AppCompatActivity.getBindingT(
    index: Int
): BINDING {
    val clazz = javaClass.getTClass(index) as Class<BINDING>
    val method = clazz.getMethod("inflate", LayoutInflater::class.java)
    val binding = method.invoke(null, LayoutInflater.from(this)) as BINDING
    if (binding is ViewDataBinding) (binding as ViewDataBinding).lifecycleOwner = this
    return binding
}

@Suppress("UNCHECKED_CAST")
fun <BINDING : ViewBinding> Fragment.getBindingT(
    index: Int,
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
): BINDING {
    val clazz = javaClass.getTClass(index) as Class<BINDING>
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
inline fun <reified BINDING : ViewBinding> ViewStub.inflateToBinding() =
    BINDING::class.java.getMethod("bind", View::class.java).invoke(null, inflate()) as BINDING
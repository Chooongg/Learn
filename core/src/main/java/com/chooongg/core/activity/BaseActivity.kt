package com.chooongg.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.chooongg.basic.LearnFrameException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB by lazy {
        var clazz: Class<*> = javaClass
        while (clazz.superclass != null) {
            val generic: Type? = clazz.genericSuperclass
            if (generic is ParameterizedType) {
                val type = generic.actualTypeArguments[0] as Class<*>
                if (ViewBinding::class.java.isAssignableFrom(type)) {
                    val method = type.getMethod("inflate", LayoutInflater::class.java)
                    return@lazy method.invoke(null, layoutInflater) as VB
                }
            }
            clazz = clazz.superclass
        }
        throw LearnFrameException("Not found binding class")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
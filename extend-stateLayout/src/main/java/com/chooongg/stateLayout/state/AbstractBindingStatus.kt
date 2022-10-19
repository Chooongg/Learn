package com.chooongg.stateLayout.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.chooongg.basic.ext.getTClass
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

abstract class AbstractBindingStatus<BINDING : ViewBinding> : AbstractState() {

    private lateinit var binding: BINDING

    override fun onBuildView(context: Context): View {
        binding = getBindingT(context)
        return binding.root
    }

    abstract fun onAttach(binding: BINDING, message: CharSequence?)
    abstract fun onChangeMessage(binding: BINDING, message: CharSequence?)
    abstract fun getReloadEventView(binding: BINDING): View?
    open fun onDetach(binding: BINDING) = Unit

    override fun onAttach(view: View, message: CharSequence?) =
        onAttach(binding, message)

    override fun onChangeMessage(view: View, message: CharSequence?) =
        onChangeMessage(binding, message)

    override fun getReloadEventView(view: View): View? =
        getReloadEventView(binding)

    override fun onDetach(view: View) =
        onDetach(binding)

    @Suppress("UNCHECKED_CAST")
    fun <BINDING : ViewBinding> getBindingT(context: Context): BINDING {
        val clazz = javaClass.getTClass(0) as Class<BINDING>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, LayoutInflater.from(context)) as BINDING
    }
}
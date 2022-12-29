package com.chooongg.form.creator

import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.style.DefaultFormStyle
import com.chooongg.form.style.FormStyle
import com.chooongg.form.style.Material3CardFormStyle

class FormAdapterCreator(private val manager: FormManager) {

    internal val adapters = ArrayList<FormGroupAdapter>()

    fun addGroup(
        name: CharSequence? = null,
        style: FormStyle = DefaultFormStyle(),
        block: FormGroupCreator.() -> Unit
    ) {
        if (!manager.styleSequence.contains(style::class)) {
            manager.styleSequence.add(style::class)
        }
        adapters.add(FormGroupAdapter(
            manager,
            style,
            manager.styleSequence.indexOf(style::class) * 100
        ).apply {
            setNewList(FormGroupCreator(name).apply(block))
        })
    }

    fun addMaterial3CardGroup(name: CharSequence? = null, block: FormGroupCreator.() -> Unit) {
        addGroup(name, Material3CardFormStyle(), block)
    }
}
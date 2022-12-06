package com.chooongg.form.bean

import android.content.Context

abstract class BaseMultipleOptionForm(type: Int, name: CharSequence, field: String?) :
    BaseOptionForm(type, name, field) {

    var separator: String = ","

    @Deprecated("弃用")
    override var content: CharSequence? = null

    val selectedKey = HashSet<CharSequence>()

    override fun transformContent(context: Context): CharSequence? {
        if (selectedKey.isEmpty()) return null
        if (options.isNullOrEmpty()) return null
        val selected = ArrayList<CharSequence>()
        options!!.forEach { if (selectedKey.contains(it.getKey())) selected.add(it.getValue()) }
        return buildString {
            for (i in 0 until selected.size) {
                if (i != 0) append(' ')
                append(selected[i])
            }
        }
    }
}
package com.chooongg.filePicker.model

import java.io.File
import java.io.FileFilter

class PickerFileFilter(private val types: Array<out String>, private val mode: Boolean) :
    FileFilter {
    override fun accept(pathname: File?): Boolean {
        if (pathname == null) return false
        if (pathname.isDirectory && !pathname.isHidden) return true
        if (pathname.name.isEmpty()) return false
        if (pathname.name[0] == '.') return false
        return if (types.isNotEmpty()) {
            var isEqualType = false
            types.forEach {
                if ((pathname.name.endsWith(it.lowercase()) || pathname.name.endsWith(it.uppercase()))
                    && !pathname.isHidden
                ) {
                    isEqualType = true
                }
            }
            if (isEqualType) mode else !mode
        } else true
    }
}
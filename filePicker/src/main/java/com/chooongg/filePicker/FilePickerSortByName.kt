package com.chooongg.filePicker

import java.io.File

class FilePickerSortByName(private val caseSensitive: Boolean = false) : Comparator<File> {
    override fun compare(o1: File?, o2: File?): Int {
        return if (o1 == null || o2 == null) {
            if (o1 == null) -1 else 1
        } else {
            when {
                o1.isDirectory && o2.isFile -> -1
                o1.isFile && o2.isDirectory -> 1
                else -> o1.name.compareTo(o2.name, caseSensitive)
            }
        }
    }
}
package com.chooongg.filePicker

import java.io.File
import java.util.Comparator

class FilePickerSortByTime : Comparator<File> {
    override fun compare(o1: File?, o2: File?): Int {
        return if (o1 == null || o2 == null) {
            if (o1 == null) -1 else 1
        } else {
            when {
                o1.isDirectory && o2.isFile -> -1
                o1.isFile && o2.isDirectory -> 1
                else -> if (o1.lastModified() > o2.lastModified()) -1 else 1
            }
        }
    }
}
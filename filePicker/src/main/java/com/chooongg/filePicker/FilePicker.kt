package com.chooongg.filePicker

import android.webkit.MimeTypeMap
import java.io.File
import java.net.URLConnection

object FilePicker {
    init {
        val file = File("asdfsdf")
        val connection = file.toURI().toURL().openConnection()
        val fileNameMap = URLConnection.getFileNameMap()
        fileNameMap.getContentTypeFor(file.name)

        MimeTypeMap.getSingleton().getMimeTypeFromExtension("")
    }
}
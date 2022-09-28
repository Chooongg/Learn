package com.chooongg.mediaPicker.provider

import android.provider.MediaStore
import com.chooongg.basic.APPLICATION

class LocalMediaProvider : IMediaProvider() {

    fun loadAlbum() {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        APPLICATION.contentResolver.query(queryUri,)
    }
}
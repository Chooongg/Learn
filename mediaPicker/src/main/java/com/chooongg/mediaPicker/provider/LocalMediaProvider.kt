package com.chooongg.mediaPicker.provider

import android.provider.MediaStore
import com.chooongg.basic.APPLICATION
import com.chooongg.mediaPicker.bean.BucketEntity
import com.chooongg.mediaPicker.bean.MediaPickerListEntity

class LocalMediaProvider : IMediaProvider() {

    fun loadAlbum() {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        APPLICATION.contentResolver.query(queryUri,)
    }

    override suspend fun getBucketCover(bucketId: String): String {3
        TODO("Not yet implemented")
    }

    override suspend fun loadAllAlbum(): MutableList<BucketEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun loadPageMediaData(
        bucketId: Long,
        page: Int,
        pageSize: Int
    ): MediaPickerListEntity<String> {
        TODO("Not yet implemented")
    }
}
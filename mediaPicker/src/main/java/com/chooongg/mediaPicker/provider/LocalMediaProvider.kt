//package com.chooongg.mediaPicker.provider
//
//import android.database.Cursor
//import com.chooongg.mediaPicker.MediaPickerConfig
//import com.chooongg.mediaPicker.bean.BucketEntity
//import com.chooongg.mediaPicker.bean.MediaEntity
//import com.chooongg.mediaPicker.bean.MediaPickerListEntity
//
//class LocalMediaProvider(config: MediaPickerConfig) : IMediaProvider(config) {
//    override suspend fun getBucketCover(bucketId: String): String? {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun loadAllBucketCover(): MutableList<BucketEntity> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun loadPageMediaData(
//        bucketId: Long,
//        page: Int,
//        pageSize: Int
//    ): MediaPickerListEntity<String> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getSelection(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun getSelectionArgs(): Array<String> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getSortOrder(): String {
//        TODO("Not yet implemented")
//    }
//
//    override fun parseLocalMedia(data: Cursor, isUsePool: Boolean): MediaEntity {
//        TODO("Not yet implemented")
//    }
//
//}
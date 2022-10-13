package com.chooongg.mediaPicker.provider

import android.net.Uri
import android.provider.MediaStore
import com.chooongg.mediaPicker.bean.BucketEntity
import com.chooongg.mediaPicker.bean.MediaPickerListEntity

abstract class IMediaProvider {


    companion object {

        protected const val COLUMN_COUNT = "count"
        protected const val COLUMN_DURATION = "duration"
        protected const val COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name"
        protected const val COLUMN_ORIENTATION = "orientation"

        val PROJECTION = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            COLUMN_DURATION,
            MediaStore.MediaColumns.SIZE,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_MODIFIED,
            COLUMN_ORIENTATION
        )

        val PROJECTION_ALL = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            COLUMN_DURATION,
            MediaStore.MediaColumns.SIZE,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_MODIFIED,
            COLUMN_ORIENTATION,
            "COUNT(*) AS $COLUMN_COUNT"
        )
    }

    protected val queryUri: Uri = MediaStore.Files.getContentUri("external")

    abstract suspend fun getBucketCover(bucketId: String): String

    abstract suspend fun loadAllAlbum(): MutableList<BucketEntity>

    abstract suspend fun loadPageMediaData(
        bucketId: Long, page: Int, pageSize: Int,
    ): MediaPickerListEntity<String>


}
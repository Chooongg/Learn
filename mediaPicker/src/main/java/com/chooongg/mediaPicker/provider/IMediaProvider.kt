//package com.chooongg.mediaPicker.provider
//
//import android.database.Cursor
//import android.net.Uri
//import android.provider.MediaStore
//import com.chooongg.mediaPicker.MediaPickerConfig
//import com.chooongg.mediaPicker.bean.BucketEntity
//import com.chooongg.mediaPicker.bean.MediaEntity
//import com.chooongg.mediaPicker.bean.MediaPickerListEntity
//import com.chooongg.mediaPicker.bean.PictureMimeType
//import java.lang.Long.max
//import java.util.*
//
//abstract class IMediaProvider(protected val config: MediaPickerConfig) {
//
//    companion object {
//        protected const val COLUMN_COUNT = "count"
//        protected const val COLUMN_DURATION = "duration"
//        protected const val COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name"
//        protected const val COLUMN_ORIENTATION = "orientation"
//        protected const val NOT_GIF = " AND (${MediaStore.MediaColumns.MIME_TYPE} != 'image/gif')"
//
//
//        val PROJECTION = arrayOf(
//            MediaStore.Files.FileColumns._ID,
//            MediaStore.MediaColumns.DATA,
//            MediaStore.MediaColumns.MIME_TYPE,
//            MediaStore.MediaColumns.WIDTH,
//            MediaStore.MediaColumns.HEIGHT,
//            COLUMN_DURATION,
//            MediaStore.MediaColumns.SIZE,
//            COLUMN_BUCKET_DISPLAY_NAME,
//            MediaStore.MediaColumns.DATE_MODIFIED,
//            COLUMN_ORIENTATION
//        )
//
//        val PROJECTION_ALL = arrayOf(
//            MediaStore.Files.FileColumns._ID,
//            MediaStore.MediaColumns.DATA,
//            MediaStore.MediaColumns.MIME_TYPE,
//            MediaStore.MediaColumns.WIDTH,
//            MediaStore.MediaColumns.HEIGHT,
//            COLUMN_DURATION,
//            MediaStore.MediaColumns.SIZE,
//            COLUMN_BUCKET_DISPLAY_NAME,
//            MediaStore.MediaColumns.DATE_MODIFIED,
//            COLUMN_ORIENTATION,
//            "COUNT(*) AS $COLUMN_COUNT"
//        )
//    }
//
//    protected val queryUri: Uri = MediaStore.Files.getContentUri("external")
//
//    abstract suspend fun getBucketCover(bucketId: String): String?
//
//    abstract suspend fun loadAllBucketCover(): MutableList<BucketEntity>
//
//    abstract suspend fun loadPageMediaData(
//        bucketId: Long, page: Int, pageSize: Int,
//    ): MediaPickerListEntity<String>
//
//    protected abstract fun getSelection(): String
//
//    protected abstract fun getSelectionArgs(): Array<String>
//
//    protected abstract fun getSortOrder(): String
//
//    protected abstract fun parseLocalMedia(data: Cursor, isUsePool: Boolean): MediaEntity
//
//    protected fun getDurationCondition() = String.format(
//        Locale.CHINA,
//        "%d <%s $COLUMN_DURATION and $COLUMN_DURATION <= %d",
//        max(0L, config.filterVideoMinSecond ?: 0L),
//        "=",
//        config.filterVideoMaxSecond ?: Long.MAX_VALUE
//    )
//
//    protected fun getFileSizeCondition() = String.format(
//        Locale.CHINA,
//        "%d <%s ${MediaStore.MediaColumns.SIZE} and ${MediaStore.MediaColumns.SIZE} <= %d",
//        max(0L, config.filterFileMinSize ?: 0L),
//        "=",
//        config.filterFileMaxSize ?: Long.MAX_VALUE
//    )
//
//    protected fun getQueryMimeCondition() = buildString {
//        val filterSet = HashSet(config.onlyQueryList ?: listOf())
//        val iterator = filterSet.iterator()
//        var index = -1
//        while (iterator.hasNext()) {
//            val value = iterator.next()
//            if (value.isEmpty()) continue
//            if (config.chooseMode == MediaPickerConfig.TYPE_IMAGE) {
//                if (value.startsWith(PictureMimeType.MIME_TYPE_PREFIX_VIDEO)) continue
//            } else if (config.chooseMode == MediaPickerConfig.TYPE_VIDEO) {
//                if (value.startsWith(PictureMimeType.MIME_TYPE_PREFIX_IMAGE)) continue
//            }
//            index++
//            append(if (index == 0) " AND " else " OR ")
//            append(MediaStore.MediaColumns.MIME_TYPE)
//            append(" ='").append(value).append("'")
//        }
//        if (config.chooseMode != MediaPickerConfig.TYPE_VIDEO) {
//            if (!config.hasGIF && !filterSet.contains(PictureMimeType.ofGIF())) append(NOT_GIF)
//        }
//    }
//}
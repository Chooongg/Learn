package com.chooongg.mediaPicker.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaEntity internal constructor(
    var id: Long? = null,
    var path: String? = null,
    var realPath: String? = null,
    var originalPath: String? = null,
    var compressPath: String? = null,
    var cropPath: String? = null,
    var watermarkPath: String? = null,
    var videoThumbnailPath: String? = null,
    var sandboxPath: String? = null,
    var mimeType: String? = null,
    var size: Long = 0L,
    var fileName: String? = null,
    var lastModificationDate: Long = 0L,
    var url: String? = null,
    // 图片视频相关
    var width: Int = 0,
    var height: Int = 0,
    // 图片相关
    var cropWidth: Int = 0,
    var cropHeight: Int = 0,
    var cropOffsetX: Int = 0,
    var cropOffsetY: Int = 0,
    var cropAspectRatio: Float = 0f,
    // 视频相关
    var duration: Long = -1,
    // 选择相关
    internal var bucketId: Long = -1,
    internal var isChecked: Boolean = false,
    internal var checkedNum: Int = 0,
    internal var isOriginal: Boolean = false,
) : Parcelable {

    /**
     * 网络图片
     */
    constructor(url: String?, mimeType: String?) : this() {
        this.url = url
        this.mimeType = mimeType
    }
}
package com.chooongg.mediaPicker.bean

data class MediaExtraInfo(
    var videoThumbnail: String? = null,
    var width: Int = 0,
    var height: Int = 0,
    var duration: Long = 0,
    var orientation: String? = null,
)
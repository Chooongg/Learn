package com.chooongg.mediaPicker

import androidx.annotation.IntDef

data class MediaPickerConfig(
    @ChooseMode val chooseMode: Int,
    var hasGIF: Boolean = false,
    var hasWEBP: Boolean = false,
    var hasBMP: Boolean = false,
    var onlyQueryList: List<String>? = null,
    var filterFileMinSize: Long? = null,
    var filterFileMaxSize: Long? = null,
    var filterVideoMinSecond: Long? = null,
    var filterVideoMaxSecond: Long? = null,
) {
    companion object {
        const val TYPE_ALL = 0
        const val TYPE_IMAGE = 1
        const val TYPE_VIDEO = 2
    }

    @IntDef(TYPE_ALL, TYPE_IMAGE, TYPE_VIDEO)
    annotation class ChooseMode
}
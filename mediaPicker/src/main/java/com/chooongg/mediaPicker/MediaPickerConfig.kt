package com.chooongg.mediaPicker

import androidx.annotation.IntDef

data class MediaPickerConfig(
    @ChooseMode val chooseMode: Int
) {
    companion object {
        private const val TYPE_ALL = 0
        private const val TYPE_IMAGE = 1
        private const val TYPE_VIDEO = 2
    }

    @IntDef(TYPE_ALL, TYPE_IMAGE, TYPE_VIDEO)
    annotation class ChooseMode
}
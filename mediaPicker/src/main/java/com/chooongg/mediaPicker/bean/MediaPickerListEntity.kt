package com.chooongg.mediaPicker.bean

data class MediaPickerListEntity<T>(
    val result: ArrayList<T>,
    val isHasMore: Boolean
)
package com.chooongg.mediaPicker.bean

import android.text.TextUtils
import com.chooongg.mediaPicker.MediaPickerConfig
import java.util.*

object PictureMimeType {

    fun isHasGif(mimeType: String?): Boolean {
        return mimeType != null && (mimeType == "image/gif" || mimeType == "image/GIF")
    }

    fun isUrlHasGif(url: String): Boolean {
        return url.lowercase(Locale.getDefault()).endsWith(".gif")
    }

    fun isUrlHasImage(url: String): Boolean {
        return (url.lowercase(Locale.getDefault()).endsWith(".jpg")
                || url.lowercase(Locale.getDefault()).endsWith(".jpeg")
                || url.lowercase(Locale.getDefault()).endsWith(".png")
                || url.lowercase(Locale.getDefault()).endsWith(".heic"))
    }

    fun isHasWebp(mimeType: String?): Boolean {
        return mimeType != null && mimeType.equals("image/webp", ignoreCase = true)
    }

    fun isUrlHasWebp(url: String): Boolean {
        return url.lowercase(Locale.getDefault()).endsWith(".webp")
    }

    fun isHasVideo(mimeType: String?): Boolean {
        return mimeType != null && mimeType.startsWith(MIME_TYPE_PREFIX_VIDEO)
    }

    fun isUrlHasVideo(url: String): Boolean {
        return url.lowercase(Locale.getDefault()).endsWith(".mp4")
    }

    fun isHasImage(mimeType: String?): Boolean {
        return mimeType != null && mimeType.startsWith(MIME_TYPE_PREFIX_IMAGE)
    }

    fun isHasBmp(mimeType: String): Boolean {
        return if (TextUtils.isEmpty(mimeType)) {
            false
        } else mimeType.startsWith(ofBMP())
                || mimeType.startsWith(ofXmsBMP())
                || mimeType.startsWith(ofWapBMP())
    }

    fun isJPEG(mimeType: String): Boolean {
        return if (TextUtils.isEmpty(mimeType)) {
            false
        } else mimeType.startsWith(MIME_TYPE_JPEG) || mimeType.startsWith(
            MIME_TYPE_JPG
        )
    }

    fun isJPG(mimeType: String): Boolean {
        return if (TextUtils.isEmpty(mimeType)) {
            false
        } else mimeType.startsWith(MIME_TYPE_JPG)
    }

    fun isHasHttp(path: String): Boolean {
        return if (TextUtils.isEmpty(path)) {
            false
        } else path.startsWith("http") || path.startsWith("https")
    }

    fun isMimeTypeSame(oldMimeType: String, newMimeType: String): Boolean {
        return if (TextUtils.isEmpty(oldMimeType)) {
            true
        } else getMimeType(oldMimeType) == getMimeType(
            newMimeType
        )
    }

    fun getMimeType(mimeType: String): Int {
        if (TextUtils.isEmpty(mimeType)) return MediaPickerConfig.TYPE_IMAGE
        return if (mimeType.startsWith(MIME_TYPE_PREFIX_VIDEO)) {
            MediaPickerConfig.TYPE_VIDEO
        } else {
            MediaPickerConfig.TYPE_IMAGE
        }
    }

    fun getLastImgSuffix(mineType: String): String {
        return try {
            mineType.substring(mineType.lastIndexOf("/")).replace("/", ".")
        } catch (e: Exception) {
            e.printStackTrace()
            JPG
        }
    }

    fun getUrlToFileName(path: String): String {
        var result = ""
        try {
            val lastIndexOf = path.lastIndexOf("/")
            if (lastIndexOf != -1) {
                result = path.substring(lastIndexOf + 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun isContent(url: String): Boolean {
        return if (TextUtils.isEmpty(url)) {
            false
        } else url.startsWith("content://")
    }

    fun ofPNG() = MIME_TYPE_PNG
    fun ofJPEG() = MIME_TYPE_JPEG
    fun ofBMP() = MIME_TYPE_BMP
    fun ofXmsBMP() = MIME_TYPE_XMS_BMP
    fun ofWapBMP() = MIME_TYPE_WAP_BMP
    fun ofGIF() = MIME_TYPE_GIF
    fun ofWEBP() = MIME_TYPE_WEBP
    fun of3GP() = MIME_TYPE_3GP
    fun ofMP4() = MIME_TYPE_MP4
    fun ofMPEG() = MIME_TYPE_MPEG
    fun ofAVI() = MIME_TYPE_AVI


    const val MIME_TYPE_PREFIX_IMAGE = "image"
    const val MIME_TYPE_PREFIX_VIDEO = "video"

    const val MIME_TYPE_IMAGE = "image/jpeg"
    const val MIME_TYPE_VIDEO = "video/mp4"

    const val MIME_TYPE_JPEG = "image/jpeg"
    const val MIME_TYPE_PNG = "image/png"
    const val MIME_TYPE_JPG = "image/jpg"
    const val MIME_TYPE_BMP = "image/bmp"
    const val MIME_TYPE_XMS_BMP = "image/x-ms-bmp"
    const val MIME_TYPE_WAP_BMP = "image/vnd.wap.wbmp"
    const val MIME_TYPE_GIF = "image/gif"
    const val MIME_TYPE_WEBP = "image/webp"

    const val MIME_TYPE_3GP = "video/3gp"
    const val MIME_TYPE_MP4 = "video/mp4"
    const val MIME_TYPE_MPEG = "video/mpeg"
    const val MIME_TYPE_AVI = "video/avi"

    const val JPEG = ".jpeg"
    const val JPG = ".jpg"
    const val PNG = ".png"
    const val WEBP = ".webp"
    const val GIF = ".gif"
    const val BMP = ".bmp"
    const val AMR = ".amr"
    const val WAV = ".wav"
    const val MP3 = ".mp3"
    const val MP4 = ".mp4"
    const val AVI = ".avi"
    const val JPEG_Q = "image/jpeg"
    const val PNG_Q = "image/png"
    const val MP4_Q = "video/mp4"
    const val AVI_Q = "video/avi"
    const val DCIM = "DCIM/Camera"
    const val CAMERA = "Camera"
}
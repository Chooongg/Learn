package com.chooongg.mediaPicker

import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import com.chooongg.mediaPicker.bean.MediaExtraInfo
import com.chooongg.mediaPicker.bean.PictureMimeType
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URLConnection
import java.util.*

object MediaUtils {

    fun getRealPathUri(id: Long, mimeType: String?): String {
        val contentUri = if (PictureMimeType.isHasImage(mimeType)) {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        } else if (PictureMimeType.isHasVideo(mimeType)) {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Files.getContentUri("external")
        }
        return ContentUris.withAppendedId(contentUri, id).toString()
    }

    fun getMimeTypeFromMediaUrl(path: String?): String? {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(path)
        var mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.lowercase(Locale.getDefault())
        )
        if (TextUtils.isEmpty(mimeType)) {
            mimeType = MediaUtils.getMimeType(File(path))
        }
        return if (TextUtils.isEmpty(mimeType)) PictureMimeType.MIME_TYPE_JPEG else mimeType
    }

    /**
     * 获取mimeType
     *
     * @param url
     * @return
     */
    fun getMimeTypeFromMediaHttpUrl(url: String): String? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        if (url.lowercase(Locale.getDefault())
                .endsWith(".jpg") || url.lowercase(Locale.getDefault()).endsWith(".jpeg")
        ) {
            return "image/jpeg"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".png")) {
            return "image/png"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".gif")) {
            return "image/gif"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".webp")) {
            return "image/webp"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".bmp")) {
            return "image/bmp"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".mp4")) {
            return "video/mp4"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".avi")) {
            return "video/avi"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".mp3")) {
            return "audio/mpeg"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".amr")) {
            return "audio/amr"
        } else if (url.lowercase(Locale.getDefault()).endsWith(".m4a")) {
            return "audio/mpeg"
        }
        return null
    }

    /**
     * 获取mimeType
     *
     * @param file
     * @return
     */
    private fun getMimeType(file: File) =
        URLConnection.getFileNameMap().getContentTypeFor(file.name)

    /**
     * 是否是长图
     *
     * @param width  图片宽度
     * @param height 图片高度
     */
    fun isLongImage(width: Int, height: Int) =
        if (width <= 0 || height <= 0) false else height > width * 3

    /**
     * 创建目录名
     *
     * @param absolutePath 资源路径
     * @return
     */
    fun generateCameraFolderName(absolutePath: String?): String {
        val folderName: String
        val cameraFile = File(absolutePath)
        folderName = if (cameraFile.parentFile != null) {
            cameraFile.parentFile.name
        } else {
            PictureMimeType.CAMERA
        }
        return folderName
    }

//    fun getImageSize(context: Context?, url: String?): MediaExtraInfo {
//        val mediaExtraInfo = MediaExtraInfo()
//        if (PictureMimeType.isHasHttp(url!!)) {
//            return mediaExtraInfo
//        }
//        var inputStream: InputStream? = null
//        try {
//            val options = BitmapFactory.Options()
//            options.inJustDecodeBounds = true
//            inputStream = if (PictureMimeType.isContent(url)) {
//                PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(url))
//            } else {
//                FileInputStream(url)
//            }
//            BitmapFactory.decodeStream(inputStream, null, options)
//            mediaExtraInfo.width = options.outWidth
//            mediaExtraInfo.height = options.outHeight
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            inputStream?.close()
//        }
//        return mediaExtraInfo
//    }
}
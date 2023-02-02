package com.chooongg.basic.utils

import android.content.Context
import android.os.Environment
import android.text.format.Formatter
import java.io.File

/**
 * 缓存数据管理器
 */
object CacheDataManager {

    /**
     * 获取总缓存大小
     */
    @Throws(Exception::class)
    fun getAppCacheSize(context: Context): String {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            context.externalCacheDir?.let { cacheSize += getFolderSize(it) }
        }
        return Formatter.formatFileSize(context, cacheSize)
    }

    /**
     * 清除所有缓存
     */
    fun clearAppCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            context.externalCacheDir?.let { deleteDir(it) }
        }
    }

    private fun getFolderSize(file: File): Long {
        var size = 0L
        try {
            file.listFiles()?.forEach {
                size += if (it.isDirectory) getFolderSize(it) else it.length()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            dir.list()?.forEach {
                val success = deleteDir(File(dir, it))
                if (!success) return false
            }
        }
        return dir.delete()
    }
}
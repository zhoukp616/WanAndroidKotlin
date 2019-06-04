package com.zkp.android.utils

import android.os.Environment
import com.zkp.android.app.App
import java.io.File
import java.math.BigDecimal

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.utils
 * @time: 2019/6/4 14:25
 * @description:
 */
class CacheUtils {

    fun getTotalCacheSize(): String {
        var cacheSize = getFolderSize(App.getContext().cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFolderSize(App.getContext().externalCacheDir!!)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (aFileList in fileList!!) {
                size += if (aFileList.isDirectory) {
                    getFolderSize(aFileList)
                } else {
                    aFileList.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }

    private fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return size.toString() + "Byte"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "KB"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }

    fun clearAllCache() {
        deleteDir(App.getContext().cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(App.getContext().externalCacheDir)
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (aChildren in children!!) {
                val success = deleteDir(File(dir, aChildren))
                if (!success) {
                    return false
                }
            }
        }
        assert(dir != null)
        return dir!!.delete()
    }

}
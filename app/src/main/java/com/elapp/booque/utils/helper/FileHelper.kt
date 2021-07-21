package com.elapp.booque.utils.helper

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import timber.log.Timber
import java.io.*

class FileHelper {

    companion object {
        const val BUFFER_SIZE: Int = 1024 * 2
    }

    fun convertUriToFilePath(context: Context?, contentUri: Uri?): String? {
        val fileName: String = getFileName(contentUri)!!
        val dir = File(
            context?.externalCacheDir.toString()
        )
        if (!dir.exists()) {
            dir.mkdirs()
        }
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(dir.toString() + File.separator + fileName)
            copy(context!!, contentUri, copyFile)
            return copyFile.absolutePath
        }
        return null
    }

    private fun getFileName(uri: Uri?): String? {
        if (uri == null) return null
        var fileName: String? = null
        val path = uri.path
        val cut = path!!.lastIndexOf('/')
        if (cut != -1) {
            fileName = path.substring(cut + 1)
        }
        return fileName
    }

    private fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri!!) ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            copyStream(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(java.lang.Exception::class, IOException::class)
    private fun copyStream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(BUFFER_SIZE)
        val `in` = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Timber.e(e.toString())
            }
            try {
                `in`.close()
            } catch (e: IOException) {
                Timber.e(e.toString())
            }
        }
        return count
    }

}
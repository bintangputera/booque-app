package com.elapp.booque.presentation.ui.book.edit

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.elapp.booque.presentation.ui.book.listener.BookListener
import com.elapp.booque.utils.global.NetworkAuthConf
import com.elapp.booque.utils.global.NetworkAuthConf.TOKEN
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import timber.log.Timber

class EditBookViewModel : ViewModel() {

    var listener: BookListener? = null

    fun validateCreateBook(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        bookName: String,
        userId: Int,
        description: String,
        address: String,
        categoryId: Int,
        thumbnail: String?,
        author: String,
        year: String,
        publisher: String,
        cityId: Int,
        provinceId: Int
    ) {
        when {
            thumbnail.isNullOrEmpty() -> {
                listener?.onFailed("Jangan lupa upload foto bukumu")
            }
            else -> {
                createBook(
                    context,
                    lifecycleOwner,
                    bookName,
                    userId,
                    description,
                    address,
                    categoryId,
                    thumbnail,
                    author,
                    year,
                    publisher,
                    cityId,
                    provinceId
                )
            }
        }
    }

    private fun createBook(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        bookName: String,
        userId: Int,
        description: String,
        address: String,
        categoryId: Int,
        thumbnail: String,
        author: String,
        year: String,
        publisher: String,
        cityId: Int,
        provinceId: Int
    ) {
        MultipartUploadRequest(context, NetworkAuthConf.BOOK_UPLOAD_BASE_URL)
            .setMethod("POST")
            .addHeader("Authorization", "Bearer $TOKEN")
            .addParameter("book_name", bookName)
            .addParameter("user_id", userId.toString())
            .addParameter("description", description)
            .addParameter("address", address)
            .addParameter("category_id", categoryId.toString())
            .addFileToUpload(thumbnail, "thumbnail", contentType = "image/*")
            .addParameter("author", author)
            .addParameter("year", year)
            .addParameter("publisher", publisher)
            .addParameter("city_id", cityId.toString())
            .addParameter("province_id", provinceId.toString())
            .subscribe(
                context = context,
                lifecycleOwner = lifecycleOwner,
                delegate = object : RequestObserverDelegate {
                    override fun onCompleted(context: Context, uploadInfo: UploadInfo) {
                        Timber.d("Sudah terupload")
                    }

                    override fun onCompletedWhileNotObserving() {
                        Timber.d("Completed while not observing")
                    }

                    override fun onError(
                        context: Context,
                        uploadInfo: UploadInfo,
                        exception: Throwable
                    ) {
                        listener?.onFailed("Gagal mengupload buku")
                    }

                    override fun onProgress(context: Context, uploadInfo: UploadInfo) {
                        listener?.onInitiating()
                    }

                    override fun onSuccess(
                        context: Context,
                        uploadInfo: UploadInfo,
                        serverResponse: ServerResponse
                    ) {
                        listener?.onSuccess(serverResponse.bodyString[2].toString())
                    }
                })
    }

}
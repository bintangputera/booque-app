package com.elapp.booque.presentation.ui.book.konfirmasi

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.elapp.booque.presentation.ui.book.listener.BookListener
import com.elapp.booque.utils.global.NetworkAuthConf
import net.gotev.uploadservice.data.UploadInfo
import net.gotev.uploadservice.network.ServerResponse
import net.gotev.uploadservice.observer.request.RequestObserverDelegate
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import timber.log.Timber

class KonfirmasiViewModel: ViewModel() {

    var listener: BookListener? = null

    fun validateCreateKonfirmasi(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        userId: Int,
        bookId: Int,
        thumbnail: String?,
        buyerId: Int
    ) {
        when {
            thumbnail.isNullOrEmpty() -> {
                listener?.onFailed("Jangan lupa upload foto bukti")
            }
            else -> {
                createKonfirmasi(context, lifecycleOwner, userId, bookId, thumbnail, buyerId)
            }
        }
    }

    private fun createKonfirmasi(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        userId: Int,
        bookId: Int,
        thumbnail: String,
        buyerId: Int
    ) {
        MultipartUploadRequest(context, NetworkAuthConf.BOOK_UPLOAD_BUKTI_BASE_URL)
            .setMethod("POST")
            .addHeader("Authorization", "Bearer ${NetworkAuthConf.TOKEN}")
            .addParameter("user_id", userId.toString())
            .addParameter("book_id", bookId.toString())
            .addFileToUpload(thumbnail, "thumbnail", contentType = "image/*")
            .addParameter("buyer_id", buyerId.toString())
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
package com.elapp.booque.utils.network

import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class GeneralErrorHandler: ErrorHandler {
    override fun getError(throwable: Throwable): NetworkState {
        return when(throwable) {
            is IOException -> NetworkState.ERROR

            is HttpException -> {
                when(throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> NetworkState(Status.NOT_FOUND)

                    else -> NetworkState(Status.UNKNOWN)
                }
            }
            else -> NetworkState((Status.UNKNOWN))
        }
    }
}
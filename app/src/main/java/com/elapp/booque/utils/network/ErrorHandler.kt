package com.elapp.booque.utils.network

interface ErrorHandler {
    fun getError(throwable: Throwable): NetworkState
}
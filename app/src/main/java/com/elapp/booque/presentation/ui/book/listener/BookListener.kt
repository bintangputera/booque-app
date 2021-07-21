package com.elapp.booque.presentation.ui.book.listener

interface BookListener {
    fun onInitiating()
    fun onSuccess(message: String)
    fun onFailed(message: String)
}
package com.elapp.booque.data.entity.response.book

import com.elapp.booque.data.entity.book.Book

data class ResponseDetailBuku(
    val error: Int,
    val msg: String,
    val data: Book
)
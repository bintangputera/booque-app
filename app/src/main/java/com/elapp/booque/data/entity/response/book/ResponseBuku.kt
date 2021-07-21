package com.elapp.booque.data.entity.response.book

import com.elapp.booque.data.entity.book.Book

data class ResponseBuku(
    val error: String,
    val msg: String,
    var data: List<Book>
)
package com.elapp.booque.presentation.ui.book.listener

import com.elapp.booque.data.entity.book.Book

interface ItemListener {
    fun onItemCLicked(book: Book)
}
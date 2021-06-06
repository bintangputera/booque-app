package com.elapp.booque.data.source

import androidx.paging.DataSource
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.utils.global.GlobalEventHandler
import io.reactivex.disposables.CompositeDisposable

class BookDataSourceFactory(
    private val bookService: BookService,
    private val keyword: String,
    private val compositeDisposable: CompositeDisposable
): DataSource.Factory<Int, Book>() {
    val bookLiveDataSource = GlobalEventHandler<BookDataSource>()

    override fun create(): DataSource<Int, Book> {
        val bookDataSource = BookDataSource(bookService, keyword, compositeDisposable)

        bookLiveDataSource.postValue(bookDataSource)
        return bookDataSource
    }

}
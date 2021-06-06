package com.elapp.booque.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.data.source.BookDataSourceFactory
import com.elapp.booque.utils.global.NetworkAuthConf.ITEM_PER_PAGE
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class BookRepository @Inject constructor(val bookService: BookService) {

    @Inject
    lateinit var bookDataSourceFactory: BookDataSourceFactory

    fun requestDataListBook(
        keyword: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<Book>> {

        lateinit var resultDataBook: LiveData<PagedList<Book>>

        val config = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .setEnablePlaceholders(false)
            .build()

        resultDataBook = LivePagedListBuilder(bookDataSourceFactory, config).build()

        return resultDataBook
    }

}
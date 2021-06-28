package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class BookDataSource  (
    private val bookService: BookService,
    private val keyword: String,
    private val compositeDisposable: CompositeDisposable
) : RxPagingSource<Int, Book>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Book>> {
        TODO("Not yet implemented")
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        TODO("Not yet implemented")
    }


}
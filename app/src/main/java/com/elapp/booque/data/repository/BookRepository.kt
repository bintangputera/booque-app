package com.elapp.booque.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.data.source.BookDataSource
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.Flowable

class BookRepository (val bookService: BookService) {

    companion object {
        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(bookService: BookService): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(bookService)
            }
    }

    var networkState: GlobalEventHandler<NetworkState> = GlobalEventHandler()

    fun requestDataListBook(
        keyword: String
    ): Flowable<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = FIRST_PAGE,
            pagingSourceFactory = { BookDataSource(bookService, keyword) }
        ).flowable
    }

}
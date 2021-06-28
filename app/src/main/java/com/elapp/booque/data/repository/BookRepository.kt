package com.elapp.booque.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.global.NetworkAuthConf.ITEM_PER_PAGE
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class BookRepository (val bookService: BookService) {


    var networkState: GlobalEventHandler<NetworkState> = GlobalEventHandler()

    fun requestDataListBook(
        keyword: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<Book>> {
        TODO("Refactoring")
    }

}
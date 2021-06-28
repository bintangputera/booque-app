package com.elapp.booque.presentation.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.repository.BookRepository
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class BookViewModel(
    private val bookRepository: BookRepository,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    fun getListDataBook(
        keyword: String
    ): LiveData<PagedList<Book>> {
        return bookRepository.requestDataListBook(keyword, compositeDisposable)
    }

}
package com.elapp.booque.presentation.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.repository.BookRepository
import io.reactivex.disposables.CompositeDisposable

class BookViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    fun getListDataBook(
        compositeDisposable: CompositeDisposable,
        keyword: String
    ): LiveData<PagedList<Book>> {
        return bookRepository.requestDataListBook(keyword, compositeDisposable)
    }

}
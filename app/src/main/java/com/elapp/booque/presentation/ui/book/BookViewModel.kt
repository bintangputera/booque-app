package com.elapp.booque.presentation.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.repository.BookRepository
import com.elapp.booque.presentation.ui.book.listener.BookListener
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class BookViewModel(
    private val bookRepository: BookRepository,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    var listener: BookListener? = null

    fun getListDataBook(
        keyword: String
    ): LiveData<PagingData<Book>> {
        val result = MutableLiveData<PagingData<Book>>()
        compositeDisposable.add(bookRepository.requestDataListBook(keyword).subscribe(
            {
                result.postValue(it)
            }, {
                val message = listener?.onFailed(it.message.toString())
            }
        ))
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
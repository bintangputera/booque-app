package com.elapp.booque.presentation.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.entity.response.book.Category
import com.elapp.booque.data.entity.transaction.Transaksi
import com.elapp.booque.data.repository.BookRepository
import com.elapp.booque.presentation.ui.book.listener.BookListener
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class BookViewModel(
    private val bookRepository: BookRepository,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    var listener: BookListener? = null

    fun getListDataBook(): LiveData<PagingData<Book>> {
        val result = MutableLiveData<PagingData<Book>>()
        compositeDisposable.add(bookRepository.requestDataListBook().subscribe(
            {
                result.postValue(it)
            }, {
                val message = listener?.onFailed(it.message.toString())
            }
        ))
        return result
    }

    fun getListDataBukuku(
        userId: Int
    ): LiveData<PagingData<Book>> {
        val result = MutableLiveData<PagingData<Book>>()
        compositeDisposable.add(bookRepository.requestDataListBukuku(userId).subscribe(
            {
                result.postValue(it)
            }, {
                Timber.e("Error occured : ${it.message}")
            }
        ))
        return result
    }

    fun getListTransaksi(userId: Int): LiveData<PagingData<Transaksi>> {
        val result = MutableLiveData<PagingData<Transaksi>>()
        compositeDisposable.add(bookRepository.requestListTransaksi(userId).subscribe(
            {
                result.postValue(it)
            }, {
                Timber.e("Error occured : ${it.message}")
            }
        ))
        return result
    }

    fun getListCategory(): LiveData<PagingData<Category>> {
        val result = MutableLiveData<PagingData<Category>>()
        compositeDisposable.add(bookRepository.requestListCategory().subscribe(
            {
                result.postValue(it)
            }, {
                Timber.e("Error occured : ${it.message}")
            }
        ))
        return result
    }

    fun getDetailBuku(bookId: Int): LiveData<Book> {
        return bookRepository.requestBookDetail(bookId, compositeDisposable)
    }

    val networkState by lazy {
        bookRepository.networkState
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
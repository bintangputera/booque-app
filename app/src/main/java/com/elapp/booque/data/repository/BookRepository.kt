package com.elapp.booque.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.entity.response.book.Category
import com.elapp.booque.data.entity.transaction.Transaksi
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.data.source.BookDataSource
import com.elapp.booque.data.source.CategoryDataSource
import com.elapp.booque.data.source.TransaksiDataSource
import com.elapp.booque.data.source.UserBookDataSource
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Flow

class BookRepository(val bookService: BookService) {

    companion object {
        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(bookService: BookService): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(bookService)
            }
    }

    var networkState: GlobalEventHandler<NetworkState> = GlobalEventHandler()

    fun requestDataListBook(): Flowable<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = FIRST_PAGE,
            pagingSourceFactory = { BookDataSource(bookService) }
        ).flowable
    }

    fun requestDataListBukuku(
        userId: Int
    ): Flowable<PagingData<Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = FIRST_PAGE,
            pagingSourceFactory = { UserBookDataSource(userId, bookService) }
        ).flowable
    }

    fun requestListCategory(): Flowable<PagingData<Category>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = FIRST_PAGE,
            pagingSourceFactory = { CategoryDataSource(bookService) }
        ).flowable
    }

    fun requestListTransaksi(userId: Int): Flowable<PagingData<Transaksi>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = FIRST_PAGE,
            pagingSourceFactory = { TransaksiDataSource(userId, bookService) }
        ).flowable
    }

    fun requestBookDetail(
        bookId: Int,
        compositeDisposable: CompositeDisposable
    ): LiveData<Book> {
        val result = MutableLiveData<Book>()
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(bookService.getBookDetail(bookId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { it.data }
            .subscribe(
                {
                    result.postValue(it)
                    networkState.postValue(NetworkState.LOADED)
                    Timber.d("Detail data loaded")
                }, {
                    Timber.e("Error occured : ${it.message}")
                    networkState.postValue(NetworkState.ERROR)
                }
            )
        )
        return result
    }

}
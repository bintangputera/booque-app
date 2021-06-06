package com.elapp.booque.data.source

import androidx.paging.PageKeyedDataSource
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import com.elapp.booque.utils.global.NetworkAuthConf.ITEM_PER_PAGE
import com.elapp.booque.utils.network.GeneralErrorHandler
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class BookDataSource  (
    private val bookService: BookService,
    private val keyword: String,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Book>() {

    val networkState: GlobalEventHandler<NetworkState> = GlobalEventHandler()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Book>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(bookService.getBookList(
            keyword
        ).subscribeOn(Schedulers.io()).subscribe(
            {
                if (it.result.size < ITEM_PER_PAGE) {
                    callback.onResult(it.result, null, null)
                    Timber.d("Check data buku : ${it.result.size}")
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    callback.onResult(it.result, null, FIRST_PAGE + 1)
                    networkState.postValue(NetworkState.LOADED)
                }
            }, {
                Timber.e("Error $it")
                val error = GeneralErrorHandler().getError(it)
                networkState.postValue(error)
            }
        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            bookService.getBookList(
                keyword
            ).subscribeOn(Schedulers.io()).subscribe(
                {
                    if (it.result.size < ITEM_PER_PAGE) {
                        callback.onResult(it.result,  null)
                        networkState.postValue(NetworkState.LOADING)
                    } else {
                        val emptyList = Collections.emptyList<Book>()
                        callback.onResult(emptyList, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }
                },  {
                    Timber.e("Error : $it")
                    val error = GeneralErrorHandler().getError(it)
                    networkState.postValue(error)
                }
            )
        )
    }

}
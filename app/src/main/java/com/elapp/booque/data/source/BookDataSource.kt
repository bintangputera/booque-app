package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class BookDataSource  (
    private val bookService: BookService
) : RxPagingSource<Int, Book>() {

    val eventHandler = GlobalEventHandler<NetworkState>()

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Book>> {
        val page = params.key ?: FIRST_PAGE
        eventHandler.postValue(NetworkState.LOADING)
        return bookService.getBookList()
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Book>> {
                LoadResult.Page(
                    data = it.data,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (page <= it.data.size) null else page + 1
                )
            }.onErrorReturn { e ->
                when(e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }
}
package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.entity.transaction.Transaksi
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.utils.global.NetworkAuthConf
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class TransaksiDataSource(
    private val userId: Int,
    private val bookService: BookService
): RxPagingSource<Int, Transaksi>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Transaksi>> {
        val page = params.key ?: NetworkAuthConf.FIRST_PAGE
        return bookService.getAllTransaksi(userId)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Transaksi>> {
                LoadResult.Page(
                    data = it.msg,
                    prevKey = if (page == NetworkAuthConf.FIRST_PAGE) null else page - 1,
                    nextKey = if (page <= it.msg.size) null else page + 1
                )
            }.onErrorReturn { e ->
                when(e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Transaksi>): Int? {
        return state.anchorPosition
    }

}
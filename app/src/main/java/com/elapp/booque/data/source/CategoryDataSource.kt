package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.response.book.Category
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class CategoryDataSource (
    private val bookService: BookService
): RxPagingSource<Int, Category>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Category>> {
        val page = params.key ?: FIRST_PAGE
        return bookService.getAllCategories()
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Category>> {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (page <= it.size) null else page + 1
                )
            }.onErrorReturn { e ->
                when(e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }

}
package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.data.service.article.BlogService
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class BlogDataSource(private val apiService: BlogService): RxPagingSource<Int, Blog>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Blog>> {
        val page = params.key ?: FIRST_PAGE
        return apiService.requestBlogList()
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Blog>> {
                LoadResult.Page(
                    data = it.data,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (page <= it.data.size) null else page + 1
                )
            }.onErrorReturn { e ->
                when (e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Blog>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }
}
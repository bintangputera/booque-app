package com.elapp.booque.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.data.service.article.BlogService
import com.elapp.booque.data.source.BlogDataSource
import com.elapp.booque.utils.global.NetworkAuthConf
import io.reactivex.Flowable

class BlogRepository(val blogService: BlogService) {

    companion object {
        @Volatile
        private var instance: BlogRepository? = null
        fun getInstance(blogService: BlogService): BlogRepository =
            instance ?: synchronized(this) {
                instance ?: BlogRepository(blogService)
            }
    }

    fun requestBookListData(): Flowable<PagingData<Blog>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = NetworkAuthConf.FIRST_PAGE,
            pagingSourceFactory = {
                BlogDataSource(blogService)
            }
        ).flowable
    }

}
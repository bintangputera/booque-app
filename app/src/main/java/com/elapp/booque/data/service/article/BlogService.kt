package com.elapp.booque.data.service.article

import com.elapp.booque.data.entity.response.blog.ResponseBlog
import io.reactivex.Single
import retrofit2.http.GET

interface BlogService {

    @GET("api/v1/blog")
    fun requestBlogList(): Single<ResponseBlog>

}
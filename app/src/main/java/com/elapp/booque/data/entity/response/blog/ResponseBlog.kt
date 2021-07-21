package com.elapp.booque.data.entity.response.blog

import com.elapp.booque.data.entity.blog.Blog

data class ResponseBlog(
    val error: Int,
    val data: List<Blog>
)

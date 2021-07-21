package com.elapp.booque.presentation.ui.article.listener

import com.elapp.booque.data.entity.blog.Blog

interface BlogListener {
    fun onItemClicked(blog: Blog)
}
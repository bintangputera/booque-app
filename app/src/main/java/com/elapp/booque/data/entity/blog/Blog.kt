package com.elapp.booque.data.entity.blog

import com.google.gson.annotations.SerializedName

data class Blog(
    val id: Int,
    @SerializedName("article_name")
    val articleName: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("category_name")
    val categoryName: String
)

package com.elapp.booque.data.entity.response.book

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    @SerializedName("category_name")
    val categoryName: String
)

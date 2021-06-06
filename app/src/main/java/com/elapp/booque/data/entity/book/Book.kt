package com.elapp.booque.data.entity.book

import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    @SerializedName("book_name")
    val bookName: String,
    val description: String,
    val address: String,
    val status: String,
    val thumbnail: String,
    val author: String,
    var year: Int,
    var publisher: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("kota_buku")
    val kotaBuku: String,
    @SerializedName("provinsi_buku")
    val provinsiBuku: String,
    @SerializedName("full_name")
    val fullName: String,
    val phone: String,
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("province_name")
    val provinceName: String
)
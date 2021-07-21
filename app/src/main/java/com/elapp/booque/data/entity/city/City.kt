package com.elapp.booque.data.entity.city

import com.google.gson.annotations.SerializedName

data class City(
    val id: Int,
    @SerializedName("province_id")
    val provinceId: Int,
    @SerializedName("city_name")
    val cityName: String,
    val latitude: String,
    val longitude: String
)

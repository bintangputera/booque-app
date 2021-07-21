package com.elapp.booque.data.entity.province

import com.google.gson.annotations.SerializedName

data class Province(
    val id: Int,
    @SerializedName("province_name")
    val provinceName: String
)

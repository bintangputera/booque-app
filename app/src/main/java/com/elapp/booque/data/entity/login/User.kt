package com.elapp.booque.data.entity.login

import com.google.gson.annotations.SerializedName

data class User (
    @field:SerializedName("user_id")
    var userId: Int,
    @field:SerializedName("full_name")
    var fullName: String,

    var address: String,
    var phone: String,

    @field:SerializedName("city_id")
    var cityId: Int,
    @field:SerializedName("province_id")
    var provinceId: Int,
    @field:SerializedName("created_at")
    var createdAt: String,
    @field:SerializedName("updated_at")
    var updatedAt: String

)
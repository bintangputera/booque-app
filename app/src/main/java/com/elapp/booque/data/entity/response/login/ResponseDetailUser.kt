package com.elapp.booque.data.entity.response.login

import com.elapp.booque.data.entity.login.User
import com.google.gson.annotations.SerializedName

data class ResponseDetailUser(
    @field:SerializedName("result")
    var data: User
)

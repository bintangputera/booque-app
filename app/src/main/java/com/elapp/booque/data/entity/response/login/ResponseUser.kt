package com.elapp.booque.data.entity.response.login

import com.elapp.booque.data.entity.login.User
import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @field:SerializedName("user")
    var data: User
)
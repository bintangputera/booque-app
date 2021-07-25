package com.elapp.booque.data.entity.response.login

import androidx.lifecycle.LiveData
import com.elapp.booque.data.entity.login.User
import com.google.gson.annotations.SerializedName

data class ResponseUser(
    var status: Int,
    var msg: String,
    @field:SerializedName("user")
    var data: User
)
package com.elapp.booque.presentation.ui.account.handler

import androidx.lifecycle.LiveData
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User

interface RegisterListener {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}
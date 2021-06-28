package com.elapp.booque.presentation.ui.account.handler

import androidx.lifecycle.LiveData
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User

interface AuthListener {
    fun onAuthenticating()
    fun onSuccess(email: String, password: String, response: LiveData<User>)
    fun onFailure(message: String)
}
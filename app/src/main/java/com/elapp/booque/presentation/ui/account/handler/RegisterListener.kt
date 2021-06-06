package com.elapp.booque.presentation.ui.account.handler

import androidx.lifecycle.LiveData
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User

interface RegisterListener {
    fun onSuccess(credential: Credential, response: LiveData<User>)
    fun onFailure(message: String)
}
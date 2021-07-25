package com.elapp.booque.presentation.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.data.entity.response.login.ResponseUser
import com.elapp.booque.data.repository.LoginRepository
import com.elapp.booque.presentation.ui.account.handler.AuthListener
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FormViewModel(
    private val loginRepository: LoginRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    var auth: AuthListener? = null

    fun oauthLogin(
        email: String
    ): LiveData<ResponseUser> {
        return loginRepository.oauthRequestLogin(email, compositeDisposable)
    }

    fun loginRequest(email: String, password: String): LiveData<ResponseUser> {
        return loginRepository.requestLogin(email, password, compositeDisposable)
    }

    val loginNetworkState: LiveData<NetworkState> by lazy {
        loginRepository.networkState
    }

    override fun onCleared() {
        Timber.d("View Model Cleared")
        super.onCleared()
        compositeDisposable.clear()
    }

}
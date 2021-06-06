package com.elapp.booque.presentation.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.data.repository.LoginRepository
import com.elapp.booque.presentation.ui.account.handler.AuthListener
import com.elapp.booque.utils.network.NetworkState
import com.elapp.booque.utils.network.NetworkState.Companion.ERROR
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class FormViewModel(
    private val loginRepository: LoginRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var auth: AuthListener? = null

    fun oauthLogin(
        email: String
    ) {
        if (email.isEmpty()) {
            auth?.onFailure("Email kosong")
            return
        }
        else {
            val loginResult = loginRepository.oauthRequestLogin(email, compositeDisposable)
            val credential = Credential(email, "")
            auth?.onSuccess(credential, loginResult)
        }
    }

    fun loginRequest() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() ) {
            auth?.onFailure("Masih ada data yang kosong")
            return
        } else {
            val loginResult = loginRepository.requestLogin(getCredential(), compositeDisposable)
            auth?.onSuccess(getCredential(), loginResult)
        }
    }

    private fun getCredential(): Credential {
        return Credential(
            email = email!! ,
            password = password!!
        )
    }

    val loginNetworkState: LiveData<NetworkState> by lazy {
        loginRepository.networkState
    }

    override fun onCleared() {
        Timber.d("View Model Cleared")
        super.onCleared()
        email = null
        password = null
        compositeDisposable.clear()
    }

}
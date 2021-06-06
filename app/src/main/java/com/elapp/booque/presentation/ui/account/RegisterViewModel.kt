package com.elapp.booque.presentation.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.repository.LoginRepository
import com.elapp.booque.presentation.ui.account.handler.RegisterListener
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class RegisterViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var nama: String? = null
    var email: String? = null
    var password: String? = null

    var auth : RegisterListener? = null

    fun registerRequest(
        type: String
    ) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || nama.isNullOrEmpty()) {
            auth?.onFailure("Masih ada data yang kosong")
            return
        } else {
            val registerResult = loginRepository.registerRequest(
                nama!!,
                getCredential(),
                type,
                compositeDisposable)
            auth?.onSuccess(getCredential(), registerResult)
        }
    }

    fun getCredential(): Credential {
        return Credential(
            email!!,
            password
        )
    }

    val networkState: LiveData<NetworkState> by lazy {
        loginRepository.networkState
    }

    override fun onCleared() {
        Timber.d("View Model Cleared")
        super.onCleared()
        compositeDisposable.clear()
    }

}
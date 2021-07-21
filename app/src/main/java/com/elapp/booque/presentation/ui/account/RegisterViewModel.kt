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
    private val loginRepository: LoginRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    var auth : RegisterListener? = null

    fun registerRequest(
        email: String,
        password: String,
        nama: String,
        type: String
    ) {
        if (email.isEmpty() || password.isEmpty() || nama.isEmpty()) {
            auth?.onFailure("Masih ada data yang kosong")
            return
        } else {
            val registerResult = loginRepository.registerRequest(
                nama,
                email,
                password,
                type,
                compositeDisposable)
            auth?.onSuccess("Register Berhasil, Silahkan login")
        }
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
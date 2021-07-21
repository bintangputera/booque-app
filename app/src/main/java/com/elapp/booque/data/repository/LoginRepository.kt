package com.elapp.booque.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.data.service.account.AccountService
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.network.GeneralErrorHandler
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginRepository(private val accountService: AccountService) {

    var networkState: GlobalEventHandler<NetworkState> = GlobalEventHandler()
    private val resultUser = MutableLiveData<User>()

    companion object {
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance(accountService: AccountService): LoginRepository =
            instance ?: synchronized(this) {
                instance ?: LoginRepository(accountService)
            }
    }

    fun requestLogin(
        email: String,
        password: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<User> {
        Timber.d("Loading to request login")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            accountService.loginRequest(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Timber.d("Get User ${it.data}")
                        resultUser.postValue(it.data)
                        networkState.postValue(NetworkState.LOADED)
                    }, {
                        Timber.e("Error while login $it")
                        val error = GeneralErrorHandler().getError(it)
                        networkState.postValue(error)
                    }
                )
        )
        return resultUser
    }

    fun oauthRequestLogin(
        email: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<User> {
        Timber.d("Oauth login requested")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(accountService.oAuthLoginRequest(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Timber.d("Oauth get user")
                    resultUser.postValue(it.data)
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    Timber.e("Error while Oauth Login $it")
                    val error = GeneralErrorHandler().getError(it)
                    networkState.postValue(error)
                }
            )
        )
        return resultUser
    }

    fun registerRequest(
        nama: String,
        email: String,
        password: String,
        type: String,
        compositeDisposable: CompositeDisposable
    ): LiveData<User> {
        Timber.d("Register processed")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable
            .add(accountService.registerRequest(nama, email, password, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Timber.d("Register done")
                        resultUser.postValue(it.result)
                        networkState.postValue(NetworkState.LOADED)
                    }, {
                        Timber.e("Error while Register ${it.message}")
                        val error = GeneralErrorHandler().getError(it)
                        networkState.postValue(error)
                    }
                )
            )
        return resultUser
    }

}
package com.elapp.booque.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.data.entity.response.login.ReponseUpdateProfile
import com.elapp.booque.data.entity.response.login.ResponseDetailUser
import com.elapp.booque.data.service.account.AccountService
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserRepository(private val apiService: AccountService) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: AccountService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }
    }

    var networkState = GlobalEventHandler<NetworkState>()

    fun requestDetailUser(
        userId: Int,
        compositeDisposable: CompositeDisposable
    ): LiveData<User> {
        val resultUser = MutableLiveData<User>()
        Timber.e("Loading data")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getProfileDetail(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { it.data }
                .subscribe(
                    {
                        Timber.d("Get profile detail : $it")
                        resultUser.postValue(it)
                        networkState.postValue(NetworkState.LOADED)
                        Timber.e("Data loaded")
                    }, {
                        Timber.e("Error occured : $it")
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
        return resultUser
    }

    fun updateProfile(
        userId: Int,
        fullName: String?,
        address: String?,
        phone: Int?,
        cityId: Int?,
        provinceId: Int?,
        compositeDisposable: CompositeDisposable
    ): LiveData<ReponseUpdateProfile> {
        val resultUser = MutableLiveData<ReponseUpdateProfile>()
        Timber.e("Initiating Update")
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.updateProfile(
                userId,
                fullName,
                address,
                phone,
                cityId,
                provinceId
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Timber.d("Updating success")
                        networkState.postValue(NetworkState.LOADED)
                        resultUser.postValue(it)
                    }, {
                        Timber.e("Error occured : ${it.message}")
                        networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
        return resultUser
    }

}
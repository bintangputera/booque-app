package com.elapp.booque.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.data.entity.response.login.ReponseUpdateProfile
import com.elapp.booque.data.repository.UserRepository
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    fun getProfileDetail(
        userId: Int
    ): LiveData<User> {
        return userRepository.requestDetailUser(userId, compositeDisposable)
    }

    fun updateProfile(
        userId: Int,
        fullName: String?,
        address: String?,
        phone: Int?,
        cityId: Int?,
        provinceId: Int?
    ): LiveData<ReponseUpdateProfile> {
        return userRepository.updateProfile(
            userId,
            fullName,
            address,
            phone,
            cityId,
            provinceId,
            compositeDisposable
        )
    }

    val networkState: LiveData<NetworkState> by lazy {
        userRepository.networkState
    }

}
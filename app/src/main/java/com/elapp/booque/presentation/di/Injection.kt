package com.elapp.booque.presentation.di

import com.elapp.booque.data.repository.*
import com.elapp.booque.data.service.RetrofitApp
import io.reactivex.disposables.CompositeDisposable

object Injection {

    private val provideBookService = RetrofitApp.getBookService()
    private val provideAccountService = RetrofitApp.getAccountService()
    private val provideProvinceService = RetrofitApp.getProvinceService()
    private val provideBlogService = RetrofitApp.getBlogService()

    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    fun provideBookRepository(): BookRepository = BookRepository.getInstance(provideBookService)
    fun provideLoginRepository(): LoginRepository =
        LoginRepository.getInstance(provideAccountService)
    fun provideUserRepository(): UserRepository = UserRepository.getInstance(provideAccountService)
    fun provideProvinceRepository(): ProvinceRepository =
        ProvinceRepository.getInstance(provideProvinceService)
    fun provideCityRepository(): CityRepository = CityRepository.getInstance(provideProvinceService)
    fun provideBlogRepository(): BlogRepository = BlogRepository.getInstance(provideBlogService)

}
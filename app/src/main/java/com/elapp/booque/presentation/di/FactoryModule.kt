package com.elapp.booque.presentation.di

import com.elapp.booque.data.repository.LoginRepository
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.data.source.BookDataSourceFactory
import com.elapp.booque.utils.global.factory.FormViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        loginRepository: LoginRepository,
        compositeDisposable: CompositeDisposable
    ): FormViewModelFactory {
        return FormViewModelFactory(loginRepository, compositeDisposable)
    }

    @Provides
    @Singleton
    fun provideBookDataSourceFactory(
        bookService: BookService,
        keyword: String,
        compositeDisposable: CompositeDisposable
    ): BookDataSourceFactory {
        return BookDataSourceFactory(bookService, keyword, compositeDisposable)
    }


}
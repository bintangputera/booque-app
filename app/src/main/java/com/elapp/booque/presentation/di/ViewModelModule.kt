package com.elapp.booque.presentation.di

import com.elapp.booque.data.repository.LoginRepository
import com.elapp.booque.presentation.ui.account.FormViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun provideCompositeDisposable() : CompositeDisposable{
        return CompositeDisposable()
    }

    @Singleton
    @Provides
    fun provideFormViewModel(
        loginRepository: LoginRepository,
        compositeDisposable: CompositeDisposable
    ): FormViewModel {
        return FormViewModel(loginRepository, compositeDisposable)
    }

}
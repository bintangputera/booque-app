package com.elapp.booque.utils.global.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.data.repository.BookRepository
import com.elapp.booque.data.repository.LoginRepository
import com.elapp.booque.presentation.ui.account.FormViewModel
import com.elapp.booque.presentation.ui.account.RegisterViewModel
import com.elapp.booque.presentation.ui.book.BookViewModel
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val loginRepository: LoginRepository,
    private val bookRepository: BookRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(loginRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(loginRepository) as T
            }
            else -> {
                throw Throwable("Unknown view model class : " + modelClass.name)
            }
        }
    }
}
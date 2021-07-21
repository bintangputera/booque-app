package com.elapp.booque.utils.global.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.data.repository.*
import com.elapp.booque.presentation.di.Injection
import com.elapp.booque.presentation.ui.account.FormViewModel
import com.elapp.booque.presentation.ui.account.RegisterViewModel
import com.elapp.booque.presentation.ui.article.BlogViewModel
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.presentation.ui.book.add.AddBookViewModel
import com.elapp.booque.presentation.ui.city.CityViewModel
import com.elapp.booque.presentation.ui.profile.ProfileViewModel
import com.elapp.booque.presentation.ui.province.ProvinceViewModel
import io.reactivex.disposables.CompositeDisposable

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val loginRepository: LoginRepository,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val provinceRepository: ProvinceRepository,
    private val cityRepository: CityRepository,
    private val blogRepository: BlogRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance : ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            (instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideLoginRepository(),
                    Injection.provideBookRepository(),
                    Injection.provideUserRepository(),
                    Injection.provideProvinceRepository(),
                    Injection.provideCityRepository(),
                    Injection.provideBlogRepository(),
                    Injection.provideCompositeDisposable()
                )
            })
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                FormViewModel(loginRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(loginRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(BookViewModel::class.java) -> {
                BookViewModel(bookRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(AddBookViewModel::class.java) -> {
                AddBookViewModel() as T
            }
            modelClass.isAssignableFrom(ProvinceViewModel::class.java) -> {
                ProvinceViewModel(provinceRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(CityViewModel::class.java) -> {
                CityViewModel(cityRepository, compositeDisposable) as T
            }
            modelClass.isAssignableFrom(BlogViewModel::class.java) -> {
                BlogViewModel(blogRepository, compositeDisposable) as T
            }
            else -> {
                throw Throwable("Unknown view model class : " + modelClass.name)
            }
        }
    }
}
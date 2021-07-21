package com.elapp.booque.presentation.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.data.repository.BlogRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class BlogViewModel(
    private val blogRepository: BlogRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    fun getListDataBlog(): LiveData<PagingData<Blog>> {
        val result = MutableLiveData<PagingData<Blog>>()
        compositeDisposable.add(
            blogRepository.requestBookListData()
                .subscribe(
                    {
                        result.postValue(it)
                    }, {
                        Timber.e("Error occured : ${it.message}")
                    }
                )
        )
        return result
    }

}
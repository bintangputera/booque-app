package com.elapp.booque.presentation.ui.province

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.data.repository.ProvinceRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ProvinceViewModel(
    private val provinceRepository: ProvinceRepository,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    fun getListDataProvince(): LiveData<PagingData<Province>> {
        val result = MutableLiveData<PagingData<Province>>()
        compositeDisposable.add(provinceRepository.requestDataListProvince()
            .subscribe(
                {
                    result.postValue(it)
                }, {
                    Timber.e(it)
                }
            )
        )
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
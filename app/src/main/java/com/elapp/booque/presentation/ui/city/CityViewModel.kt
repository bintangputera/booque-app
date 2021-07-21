package com.elapp.booque.presentation.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.elapp.booque.data.entity.city.City
import com.elapp.booque.data.repository.CityRepository
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class CityViewModel(
    private val cityRepository: CityRepository,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    fun getListDataCity(
        provinceId: Int
    ): LiveData<PagingData<City>> {
        val result = MutableLiveData<PagingData<City>>()
        compositeDisposable.add(cityRepository.requestListDataCity(provinceId)
            .subscribe(
                {
                    result.postValue(it)
                }, {
                    Timber.e("Error occured : $it")
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
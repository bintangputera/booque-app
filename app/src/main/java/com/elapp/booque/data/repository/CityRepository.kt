package com.elapp.booque.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.elapp.booque.data.entity.city.City
import com.elapp.booque.data.service.province.ProvinceService
import com.elapp.booque.data.source.CityDataSource
import com.elapp.booque.utils.global.NetworkAuthConf
import io.reactivex.Flowable

class CityRepository(private val provinceService: ProvinceService) {

    companion object {
        @Volatile
        private var instance: CityRepository? = null
        fun getInstance(provinceService: ProvinceService): CityRepository =
            instance ?: synchronized(this) {
                instance ?: CityRepository(provinceService)
            }
    }
    
    fun requestListDataCity(
        provinceId: Int
    ): Flowable<PagingData<City>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = NetworkAuthConf.FIRST_PAGE,
            pagingSourceFactory = { CityDataSource(provinceId, provinceService) }
        ).flowable
    }

}
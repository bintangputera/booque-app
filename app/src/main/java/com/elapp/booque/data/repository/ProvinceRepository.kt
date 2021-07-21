package com.elapp.booque.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.data.service.province.ProvinceService
import com.elapp.booque.data.source.ProvinceDataSource
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import io.reactivex.Flowable

class ProvinceRepository(private val provinceService: ProvinceService) {

    companion object {
        @Volatile
        private var instance: ProvinceRepository? = null
        fun getInstance(provinceService: ProvinceService): ProvinceRepository =
            instance ?: synchronized(this) {
                instance ?: ProvinceRepository(provinceService)
            }
    }

    fun requestDataListProvince(): Flowable<PagingData<Province>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            initialKey = FIRST_PAGE,
            pagingSourceFactory = { ProvinceDataSource(provinceService) }
        ).flowable
    }

}
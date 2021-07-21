package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.city.City
import com.elapp.booque.data.service.province.ProvinceService
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class CityDataSource(
    private val provinceId: Int,
    private val provinceService: ProvinceService
) : RxPagingSource<Int, City>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, City>> {
        val page = params.key ?: FIRST_PAGE
        return provinceService.getListCity(provinceId)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, City>> {
                LoadResult.Page(
                    data = it,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (page <= it.size) null else page + 1
                )
            }.onErrorReturn { e ->
                when (e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, City>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }
}
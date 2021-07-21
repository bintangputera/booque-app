package com.elapp.booque.data.source

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.data.service.province.ProvinceService
import com.elapp.booque.utils.global.GlobalEventHandler
import com.elapp.booque.utils.global.NetworkAuthConf.FIRST_PAGE
import com.elapp.booque.utils.network.NetworkState
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class ProvinceDataSource(
    private val provinceService: ProvinceService
) : RxPagingSource<Int, Province>() {

    val eventHandler = GlobalEventHandler<NetworkState>()

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Province>> {
        val page = params.key ?: FIRST_PAGE
        return provinceService.getListProvince()
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Province>> {
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

    override fun getRefreshKey(state: PagingState<Int, Province>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id
        }
    }

}
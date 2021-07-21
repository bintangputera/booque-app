package com.elapp.booque.data.service.province

import com.elapp.booque.data.entity.city.City
import com.elapp.booque.data.entity.province.Province
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProvinceService {

    @GET("api/v1/provinsi")
    fun getListProvince(): Single<List<Province>>

    @GET("api/v1/kota/{province_id}")
    fun getListCity(
        @Path("province_id") provinceId: Int
    ): Single<List<City>>

}
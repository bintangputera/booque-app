package com.elapp.booque.data.service.book

import com.elapp.booque.data.entity.response.book.ResponseBuku
import com.elapp.booque.data.entity.response.book.Category
import com.elapp.booque.data.entity.response.book.ResponseDetailBuku
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface BookService {

    @POST("api/v1/semua-buku")
    fun getBookList(): Single<ResponseBuku>

    @POST("api/v1/buku-sekitar")
    fun getBookSekitar(
        cityName: String
    )

    @POST("api/v1/buku-ku/{id}")
    fun getUserBook(
        @Path("id") id: Int
    ): Single<ResponseBuku>

    @POST("api/v1/buku-detail")
    @FormUrlEncoded
    fun getBookDetail(
        @Field("id") bookId: Int
    ): Observable<ResponseDetailBuku>

    @GET("api/v1/semua-category")
    fun getAllCategories(): Single<List<Category>>

    @POST("api/v1/update-buku/{id}")
    @FormUrlEncoded
    fun updateBook(
        @Path("id") bookId: Int,
        @Field("book_name") bookName: String,
        @Field("description") description: String,
        @Field("address") address: String,
        @Field("category_id") categoryId: Int,
        @Field("status") status: Int,
        @Field("thumbnail") thumbnail: String?,
        @Field("author") author: String,
        @Field("year") year: Int,
        @Field("publisher") publisher: String,
        @Field("city_id") cityId: Int,
        @Field("province_id") provinceId: Int
    ): Observable<ResponseBuku>

}
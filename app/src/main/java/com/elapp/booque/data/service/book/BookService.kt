package com.elapp.booque.data.service.book

import com.elapp.booque.data.entity.response.book.ResponseBuku
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface BookService {

    @POST("api/v1/buku")
    @FormUrlEncoded
    fun getBookList(
        @Field("book_name")bookName: String?
    ): Single<ResponseBuku>

    @POST("api/v1/buku-sekitar")
    fun getBookSekitar(
        cityName: String
    )

    @POST("api/v1/buku-ku/{id}")
    fun getUserBook(
        @Path("id") id: String
    ): Single<ResponseBuku>

}
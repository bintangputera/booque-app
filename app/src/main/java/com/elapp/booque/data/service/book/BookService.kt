package com.elapp.booque.data.service.book

import com.elapp.booque.data.entity.response.book.ResponseBuku
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BookService {

    @POST("api/buku")
    @FormUrlEncoded
    fun getBookList(
        @Field("book_name")bookName: String?
    ): Observable<ResponseBuku>

    @POST("api/buku-sekitar")
    fun getBookSekitar(
        cityName: String
    )

}
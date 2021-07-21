package com.elapp.booque.data.service

import com.elapp.booque.data.service.account.AccountService
import com.elapp.booque.data.service.article.BlogService
import com.elapp.booque.data.service.book.BookService
import com.elapp.booque.data.service.province.ProvinceService
import com.elapp.booque.utils.global.NetworkAuthConf.BASE_URL
import com.elapp.booque.utils.global.NetworkAuthConf.TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class RetrofitApp {
    companion object {

        private val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val client = OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            }).build()

        private val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        fun getBookService(): BookService = retrofit.create(
            BookService::class.java)

        fun getAccountService(): AccountService = retrofit.create(
            AccountService::class.java
        )

        fun getProvinceService(): ProvinceService = retrofit.create(
            ProvinceService::class.java
        )

        fun getBlogService(): BlogService = retrofit.create(
            BlogService::class.java
        )

    }
}
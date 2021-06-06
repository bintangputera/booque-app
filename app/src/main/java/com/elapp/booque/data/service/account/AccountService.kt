package com.elapp.booque.data.service.account

import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.response.login.ResponseRegister
import com.elapp.booque.data.entity.response.login.ResponseUser
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountService {

    @POST("api/login-oauth")
    @FormUrlEncoded
    fun oAuthLoginRequest(
        @Field("email") email: String
    ): Observable<ResponseUser>

   @POST("api/login")
   fun loginRequest(
       @Body request: Credential
   ): Observable<ResponseUser>

   @POST("api/daftar")
   @FormUrlEncoded
   fun registerRequest(
       @Field("nama") nama: String,
       @Field("email") email: String,
       @Field("password") password: String,
       @Field("type") type: String
   ): Observable<ResponseRegister>

   @POST("api/daftar-oauth")
   @FormUrlEncoded
   fun oAuthRegisterRequest(
       @Field("nama") nama: String,
       @Field("email") email: String
   ): Observable<ResponseUser>


}
package com.elapp.booque.data.service.account

import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.response.login.ReponseUpdateProfile
import com.elapp.booque.data.entity.response.login.ResponseDetailUser
import com.elapp.booque.data.entity.response.login.ResponseRegister
import com.elapp.booque.data.entity.response.login.ResponseUser
import io.reactivex.Observable
import retrofit2.http.*

interface AccountService {

    @POST("api/v1/login-oauth")
    @FormUrlEncoded
    fun oAuthLoginRequest(
        @Field("email") email: String
    ): Observable<ResponseUser>

   @POST("api/v1/login")
   @FormUrlEncoded
   fun loginRequest(
       @Field("email") email: String,
       @Field("password") password: String
   ): Observable<ResponseUser>

   @POST("api/v1/daftar")
   @FormUrlEncoded
   fun registerRequest(
       @Field("nama") nama: String,
       @Field("email") email: String,
       @Field("password") password: String,
       @Field("type") type: String
   ): Observable<ResponseRegister>

   @POST("api/v1/daftar-oauth")
   @FormUrlEncoded
   fun oAuthRegisterRequest(
       @Field("nama") nama: String,
       @Field("email") email: String
   ): Observable<ResponseUser>

   @POST("api/v1/profile")
   @FormUrlEncoded
   fun getProfileDetail(
       @Field("id") id: Int
   ): Observable<ResponseDetailUser>

   @PUT("api/v1/update-profile/{id}")
   @FormUrlEncoded
   fun updateProfile(
       @Path("id") id: Int,
       @Field("full_name") fullName: String?,
       @Field("address") address: String?,
       @Field("phone") phone: Int?,
       @Field("city_id") cityId: Int?,
       @Field("province_id") provinceId: Int?
   ): Observable<ReponseUpdateProfile>

}
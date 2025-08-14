package com.iftikar.mediadmin.data.remote

import com.iftikar.mediadmin.domain.model.User
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<List<User>>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id") userId: String
    ): Response<List<User>>
}
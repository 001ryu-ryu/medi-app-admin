package com.iftikar.mediadmin.data.remote

import com.iftikar.mediadmin.data.remote.model.ModifyUserResponse
import com.iftikar.mediadmin.data.remote.model.InsertProductResponseDto
import com.iftikar.mediadmin.domain.model.Product
import com.iftikar.mediadmin.domain.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<List<User>>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id") userId: String
    ): Response<List<User>>

    @FormUrlEncoded
    @PATCH("approveUser")
    suspend fun approveUser(
        @Field("user_id") userId: String,
        @Field("approve") approve: Int
    ): Response<ModifyUserResponse>

    @GET("getAllProducts")
    fun getProducts(): Call<List<Product>>

    @FormUrlEncoded
    @POST("getSpecificProd")
    suspend fun getSpecificProduct(
        @Field("product_id") productId: String
    ): Response<List<Product>>

    @FormUrlEncoded
    @POST("insertProducts")
    suspend fun insertProduct(
        @Field("name") name: String,
        @Field("price") price: Double,
        @Field("category") category: String,
        @Field("stock") stock: Int,
    ): Response<InsertProductResponseDto>

    // Block user
    @FormUrlEncoded
    @PATCH("blockUser")
    suspend fun blockUser(
        @Field("user_id") userId: String,
        @Field("block") blockModify: Int
    ): Response<ModifyUserResponse>
}










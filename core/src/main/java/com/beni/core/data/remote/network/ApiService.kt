package com.beni.core.data.remote.network

import com.beni.core.data.remote.request.ExampleRequest
import com.beni.core.data.remote.response.ServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("exaple/endpoint")
    suspend fun sampleGetRequest(): ServerResponse

    //send with json
    @POST("example/endpoint/post")
    suspend fun sampleInsertData(
        @Header("Authorization") header: String,
        @Body sampleRequest: ExampleRequest
    ): ServerResponse

    //send with form data
    @FormUrlEncoded
    @POST("example/endpoint/post")
    suspend fun sampleInsertData(
        @Header("Authorization") header: String,
        @Field("field1") field1: String,
        @Field("field2") field2: String,
    ): ServerResponse

    //uploadSingleImage
    @Multipart
    @POST("example/endpoint/postImage")
    suspend fun sampleInsertSingleImage(
        @Header("Authorization") header: String,
        @Part image: MultipartBody.Part,
        @Part("field1") field1: RequestBody,
        @Part("field2") field2: RequestBody,
    ): ServerResponse

    //uploadMultipleImage
    @Multipart
    @POST("example/endpoint/postListImage")
    suspend fun sampleInsertMultipleImage(
        @Header("Authorization") header: String,
        @Part image: List<MultipartBody.Part>,
        @Part("field1") field1: RequestBody,
        @Part("field2") field2: RequestBody,
    ): ServerResponse
}
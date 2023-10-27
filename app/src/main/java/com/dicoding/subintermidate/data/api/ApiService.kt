package com.dicoding.subintermidate.data.api

import com.dicoding.subintermidate.data.local.story.DetailStoryResponse
import com.dicoding.subintermidate.data.local.story.StoryResponse
import com.dicoding.subintermidate.data.local.story.UploadStoryResponse
import com.dicoding.subintermidate.data.local.user.LoginResponse
import com.dicoding.subintermidate.data.local.user.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): UploadStoryResponse

    @GET("stories")
    suspend fun getAllStory(
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String
    ): DetailStoryResponse
}
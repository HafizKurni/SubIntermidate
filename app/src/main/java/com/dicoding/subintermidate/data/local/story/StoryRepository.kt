package com.dicoding.subintermidate.data.local.story

import com.dicoding.subintermidate.data.api.ApiService
import com.dicoding.subintermidate.data.api.Result
import androidx.lifecycle.liveData
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository private constructor(
    private val apiService: ApiService){

    fun getAllStory() = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.getAllStory()
            emit(Result.Success(successResponse.listStory))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    fun getStoryDetail(id: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.getDetailStory(id)
            emit(Result.Success(successResponse.story))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun uploadStory(imageFile: File, description: String) = liveData {
        emit(Result.Loading)

        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody()

        val multiPartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        try {
            val successResponse = apiService.uploadStory(multiPartBody, requestBody)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}
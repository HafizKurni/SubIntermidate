package com.dicoding.subintermidate.data.local.user

import com.dicoding.subintermidate.data.api.Result
import com.dicoding.subintermidate.data.api.ApiService
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.liveData
import com.dicoding.subintermidate.data.local.UserPreference
import com.google.gson.Gson
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.login(email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(Result.Error(errorResponse.message))
        }
    }

    fun signUp(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.register(name, email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference,apiService)
            }.also { instance = it }
    }
}
package com.dicoding.subintermidate.data.local

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.subintermidate.MainViewModel
import com.dicoding.subintermidate.action.auth.LoginViewModel
import com.dicoding.subintermidate.action.auth.RegisViewModel
import com.dicoding.subintermidate.action.home.DetailViewModel
import com.dicoding.subintermidate.action.home.HomeViewModel
import com.dicoding.subintermidate.action.upload.UploadViewModel
import com.dicoding.subintermidate.data.local.story.StoryRepository
import com.dicoding.subintermidate.data.local.user.UserRepository

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
    ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T

        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T

        } else if (modelClass.isAssignableFrom(RegisViewModel::class.java)) {
            return RegisViewModel(userRepository) as T

        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository, storyRepository) as T

        } else if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(storyRepository) as T

        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(storyRepository) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val storyRepository = Injection.provideRepository1(context)
                val userRepository = Injection.provideRepository(context)
                instance ?: ViewModelFactory(userRepository, storyRepository)

            }.also { instance = it }
    }
}



package com.dicoding.subintermidate.action.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.subintermidate.data.local.story.StoryRepository
import com.dicoding.subintermidate.data.local.user.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository)
    : ViewModel(){

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }



    fun getAllStory() = storyRepository.getAllStory()
}
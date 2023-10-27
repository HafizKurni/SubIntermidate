package com.dicoding.subintermidate.action.home


import androidx.lifecycle.ViewModel
import com.dicoding.subintermidate.data.local.story.StoryRepository

class DetailViewModel  constructor(private val storyRepository: StoryRepository) : ViewModel() {

    fun getDetailStory(id: String) = storyRepository.getStoryDetail(id)

}


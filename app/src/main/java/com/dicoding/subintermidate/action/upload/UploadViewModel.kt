package com.dicoding.subintermidate.action.upload

import androidx.lifecycle.ViewModel
import com.dicoding.subintermidate.data.local.story.StoryRepository
import java.io.File

class UploadViewModel constructor(private val storyRepository: StoryRepository) : ViewModel() {
    fun uploadStory(file: File, description: String) = storyRepository.uploadStory(file, description)
}
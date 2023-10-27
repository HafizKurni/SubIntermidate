package com.dicoding.subintermidate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.subintermidate.data.local.user.User
import com.dicoding.subintermidate.data.local.user.UserRepository

class MainViewModel constructor(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }
}
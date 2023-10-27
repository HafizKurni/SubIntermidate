package com.dicoding.subintermidate.action.auth

import androidx.lifecycle.ViewModel
import com.dicoding.subintermidate.data.local.user.UserRepository

class RegisViewModel(private val repository: UserRepository) : ViewModel() {

    fun signup(name: String, email: String, password: String) = repository.signUp(name, email, password)

}
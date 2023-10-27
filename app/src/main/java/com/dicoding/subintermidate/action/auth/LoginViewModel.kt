package com.dicoding.subintermidate.action.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.subintermidate.data.local.user.User
import com.dicoding.subintermidate.data.local.user.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
    ) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email,password)

    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}
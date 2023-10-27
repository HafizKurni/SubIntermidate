package com.dicoding.subintermidate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.subintermidate.action.auth.LoginActivity
import com.dicoding.subintermidate.action.auth.RegisActivity
import com.dicoding.subintermidate.action.home.HomeActivity
import com.dicoding.subintermidate.data.local.ViewModelFactory
import com.dicoding.subintermidate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            loginButton.setOnClickListener {
                startAuthActivity(true)
            }
            signupButton.setOnClickListener {
                startAuthActivity(false)
            }

        }

        mainViewModel.getSession().observe(this) {
            if (it.isLogin && it.token.isNotEmpty()) {
                loginHome()
                finish()
            }
        }
    }

    private fun startAuthActivity(showLogin: Boolean) {
        val intent = if (showLogin) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, RegisActivity::class.java)
        }
        startActivity(intent)
    }

    private fun loginHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

}
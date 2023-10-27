package com.dicoding.subintermidate.action.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dicoding.subintermidate.data.api.Result
import com.dicoding.subintermidate.R
import com.dicoding.subintermidate.databinding.ActivityLoginBinding
import com.dicoding.subintermidate.action.home.HomeActivity
import com.dicoding.subintermidate.data.local.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            loginButton.setOnClickListener{
                showLoading(true)
                loginViewModel.login(emailEditText.text.toString(), passwordEditText.text.toString()).observe(this@LoginActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                loginViewModel.saveSession(result.data.loginResult)
                                showToast(result.data.message)
                                HomeisActivity()
                            }
                            is Result.Error -> {
                                showLoading(false)
                                showDialog(result.error)
                            }
                        }
                    }
                }
            }
        }


    }

    private fun HomeisActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.error))
            setMessage(message)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
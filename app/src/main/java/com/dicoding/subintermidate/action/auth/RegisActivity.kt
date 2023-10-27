package com.dicoding.subintermidate.action.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.subintermidate.data.local.ViewModelFactory
import com.dicoding.subintermidate.data.api.Result
import com.dicoding.subintermidate.R
import com.dicoding.subintermidate.databinding.ActivityRegisBinding

class RegisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisBinding
    private val regisViewModel by viewModels<RegisViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            signupButton.setOnClickListener {
                regisViewModel.signup(
                    nameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).observe(this@RegisActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                showLoading(true)
                            }

                            is Result.Success -> {
                                showLoading(false)
                                showToast(result.data.message)
                                clearField()
                                showDialog(binding.emailEditText.text.toString())
                                loginActivity()

                            }

                            is Result.Error -> {
                                showLoading(false)
                                showToast(result.error)
                            }
                        }
                    }
                }
                clearField()
            }
        }
    }

    private fun loginActivity() {
        Toast.makeText(this, "Daftar Akun Berhasil", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearField() {
        with(binding) {
            nameEditText.text?.clear()
            emailEditText.text?.clear()
            passwordEditText.text?.clear()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.success))
            setMessage(getString(R.string.signup_success, email))
            setPositiveButton(getString(R.string.yes)) { _, _ ->

            }
            create()
            show()
        }
    }
}

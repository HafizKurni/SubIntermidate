package com.dicoding.subintermidate.action.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.subintermidate.data.api.Result
import com.dicoding.subintermidate.data.local.ViewModelFactory
import com.dicoding.subintermidate.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val storyId = intent.getStringExtra(ID)

        if (storyId != null) {
            detailViewModel.getDetailStory(storyId).observe(this) { result ->
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        with(binding) {
                            Glide.with(this@DetailActivity).load(result.data.photoUrl).into(imageView)
                            titleStory.text = result.data.name
                            descStory.text = result.data.description
                        }
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }

            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ID = "idMove"
    }
}
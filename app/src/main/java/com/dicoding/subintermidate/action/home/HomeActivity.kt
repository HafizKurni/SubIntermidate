package com.dicoding.subintermidate.action.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.subintermidate.R
import com.dicoding.subintermidate.action.auth.LoginActivity
import com.dicoding.subintermidate.databinding.ActivityHomeBinding
import com.dicoding.subintermidate.action.upload.UploadActivity
import com.dicoding.subintermidate.data.local.story.Story
import com.dicoding.subintermidate.feature.ShowAdapter
import com.dicoding.subintermidate.data.api.Result
import com.dicoding.subintermidate.data.local.ViewModelFactory


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: ShowAdapter
    private val homeViewModel by viewModels<HomeViewModel>{
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeAdapter = ShowAdapter()

        homeViewModel.getAllStory().observe(this) {result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                    homeAdapter.clearList()
                }

                is Result.Success -> {
                    showLoading(false)
                    homeAdapter.setList(result.data)
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }

                else ->{}
            }
        }

        with(binding) {
            buttonCamera.setOnClickListener{
                cameraActivity()
            }

            ibExit.setOnClickListener{
                showDialog()
            }

            setting.setOnClickListener{
                setting()
            }

            rvHome.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = homeAdapter
                addItemDecoration(DividerItemDecoration(this@HomeActivity, (rvHome.layoutManager as LinearLayoutManager).orientation))
            }
        }

        homeAdapter.setOnItemClickCallback(object : ShowAdapter.OnItemClickCallback{
            override fun onItemClicked(story: Story) {
                Intent(this@HomeActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.ID, story.id)
                    startActivity(it)
                }
            }
        })
    }

    private fun cameraActivity() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setting() {
        val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(mIntent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    private fun showDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.logout))
            setMessage(getString(R.string.are_you_sure))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                homeViewModel.logout()
                loginActivity()
            }
            create()
            show()
        }
    }
}
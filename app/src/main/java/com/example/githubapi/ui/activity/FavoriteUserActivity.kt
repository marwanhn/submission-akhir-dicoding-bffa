package com.example.githubapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.githubapi.adapter.FavoriteUserAdapter
import com.example.githubapi.databinding.ActivityFavoriteUserBinding
import com.example.githubapi.ui.model.FavoriteViewModel
import com.example.githubapi.ui.model.FavoriteViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(application)
        )[FavoriteViewModel::class.java]

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteViewModel.getAllFavorites().observe(this) {
            binding.apply {
                rvUser.adapter = FavoriteUserAdapter(it)

            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
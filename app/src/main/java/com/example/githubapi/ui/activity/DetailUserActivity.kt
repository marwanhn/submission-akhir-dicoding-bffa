package com.example.githubapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapi.R
import com.example.githubapi.adapter.DetailUserAdapter
import com.example.githubapi.data.database.UserEntity
import com.example.githubapi.data.response.DetailUserResponse
import com.example.githubapi.databinding.ActivityDetailUserBinding
import com.example.githubapi.ui.fragment.FollowFragment
import com.example.githubapi.ui.model.DetailViewModel
import com.example.githubapi.ui.model.FavoriteViewModel
import com.example.githubapi.ui.model.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var isUserFavorited = false
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User"

        favoriteViewModel = ViewModelProvider(this, FavoriteViewModelFactory(application)) [FavoriteViewModel::class.java]
        val currentUser = intent.getParcelableExtra<UserEntity>(EXTRA_USER)
        val login = currentUser?.login
        if (login != null) {
            detailViewModel.getGithubDetail(login)
        }
        detailViewModel.listDetailUser.observe(this) { detailList ->
            setDetailData(detailList)
        }


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }




        if (currentUser != null) {
            favoriteViewModel.getFavoriteUserByUsername(currentUser.login).observe(this) {
                isUserFavorited = currentUser.login == it
                binding.apply {
                    btnFavorite.setOnClickListener {
                        if(isUserFavorited){
                            favoriteViewModel.delete(currentUser)
                            Toast.makeText(this@DetailUserActivity, "$login telah dihapus dari Favorit", Toast.LENGTH_SHORT).show()
                        } else {
                            favoriteViewModel.insert(currentUser)
                            Toast.makeText(this@DetailUserActivity, "$login telah ditambah dari Favorit", Toast.LENGTH_SHORT).show()
                        }

                    }
                    if(isUserFavorited){
                        btnFavorite.setImageResource(R.drawable.baseline_favorite_filled_24)
                    } else {
                        btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    }

                }
            }
        }




    }

    private fun setDetailData(detailList: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailList.avatarUrl)
                .circleCrop()
                .into(ivAvatar)
            tvName.text = detailList.name ?: "No Name."
            tvUsername.text = detailList.login
            val shortFollowers = detailList.followers
            if (shortFollowers > 10000) {
                val formattedFollowers = "%.1fK".format(shortFollowers / 1000.0)
                tvFollowers.text = formattedFollowers
            } else {
                tvFollowers.text = detailList.followers.toString()
            }
            val shortFollowing = detailList.following
            if (shortFollowing > 10000) {
                val formattedFollowing = "%.1fK".format(shortFollowers / 1000.0)
                tvFollowing.text = formattedFollowing
            } else {
                tvFollowing.text = detailList.following.toString()
            }
            val shortRepository = detailList.publicRepos
            if (shortRepository > 10000) {
                "${shortRepository / 1000}.${(shortRepository % 1000) / 100}K".also {
                    tvRepository.text = it
                }
            } else {
                tvRepository.text = detailList.publicRepos.toString()
            }
            tvLocation.text = detailList.location ?: "No Location."
            tvCompany.text = detailList.company ?: "No company."

            val fragment = mutableListOf<Fragment>(
                FollowFragment.newInstance(FollowFragment.FOLLOWING),
                FollowFragment.newInstance(FollowFragment.FOLLOWERS)
            )

            val fragmentTitle = mutableListOf(
                getString(R.string.following),
                getString(R.string.followers)
            )

            val detailAdapter = DetailUserAdapter(this@DetailUserActivity, fragment)
            viewPager.adapter = detailAdapter

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = fragmentTitle[position]
            }.attach()

            tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == FollowFragment.FOLLOWERS) {
                            detailViewModel.getFollower(detailList.login)
                    } else {
                            detailViewModel.getFollowing(detailList.login)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            detailViewModel.getFollowing(detailList.login)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }


}
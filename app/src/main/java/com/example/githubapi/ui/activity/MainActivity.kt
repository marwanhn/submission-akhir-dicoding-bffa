package com.example.githubapi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.R
import com.example.githubapi.adapter.SearchUserAdapter
import com.example.githubapi.data.database.SettingPreferences
import com.example.githubapi.data.database.dataStore
import com.example.githubapi.data.response.GithubUser
import com.example.githubapi.databinding.ActivityMainBinding
import com.example.githubapi.ui.model.MainViewModel
import com.example.githubapi.ui.model.ThemesViewModel
import com.example.githubapi.ui.model.ThemesViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var themesViewModel: ThemesViewModel
    private var isDarkModeActive = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        mainViewModel.findUser("MarwanH")
        with(binding) {
            searchView.setupWithSearchBar(searchbar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                searchbar.text = searchView.text
                if(searchView.text!!.isEmpty()){
                    mainViewModel.findUser("MarwanH")
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                } else {
                    mainViewModel.findUser(searchView.text.toString())
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                }
                searchView.hide()
                false
            }
        }

        mainViewModel.listGitHubUser.observe(this) { listGitHubUser ->
            setUserData(listGitHubUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val pref = SettingPreferences.getInstance(application.dataStore)
        themesViewModel =
            ViewModelProvider(this, ThemesViewModelFactory(pref))[ThemesViewModel::class.java]
        themesViewModel.getThemeSettings().observe(this) {
            if (it) {
                isDarkModeActive = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                isDarkModeActive = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


    }


    private fun setUserData(listGitHubUser: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        for (user in listGitHubUser) {
            listUser.clear()
            listUser.addAll(listGitHubUser)
        }
        val adapter = SearchUserAdapter(listUser)
        binding.rvUser.adapter = adapter

    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun applyTheme() {
        val mode = if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val themeMenuItem = menu?.findItem(R.id.dark_mode)
        if (isDarkModeActive) {
            themeMenuItem?.setIcon(R.drawable.baseline_mode_night_24)
        } else {
            themeMenuItem?.setIcon(R.drawable.baseline_wb_sunny_24)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val launchFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(launchFavorite)
            }

            R.id.dark_mode -> {
                isDarkModeActive = !isDarkModeActive
                themesViewModel.saveThemeSetting(isDarkModeActive)
                applyTheme()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
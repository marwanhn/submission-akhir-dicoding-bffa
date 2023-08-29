package com.example.githubapi.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.adapter.SearchUserAdapter
import com.example.githubapi.data.response.GithubUser
import com.example.githubapi.databinding.ActivityMainBinding
import com.example.githubapi.ui.model.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel.findUser("MarwanH")
        setContentView(binding.root)
        supportActionBar?.hide()
        with(binding) {
            searchView.setupWithSearchBar(searchbar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchbar.text = searchView.text
                searchView.hide()
                mainViewModel.findUser(searchView.text.toString())
                Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }


        mainViewModel.listGitHubUser.observe(this) { listGitHubUser ->
            setUserData(listGitHubUser)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }





        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)



    }




    private fun setUserData(listGitHubUser: List<GithubUser>){
        val listUser = ArrayList<GithubUser>()
        for (user in listGitHubUser){
            listUser.clear()
            listUser.addAll(listGitHubUser)
        }
        val adapter = SearchUserAdapter(listUser)
        binding.rvUser.adapter = adapter

    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
package com.example.githubapi.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi.data.response.DetailUserResponse
import com.example.githubapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _listDetailUser = MutableLiveData<DetailUserResponse>()
    val listDetailUser: LiveData<DetailUserResponse> = _listDetailUser

    private val _followingData = MutableLiveData<ArrayList<DetailUserResponse>>()
    val followingData: LiveData<ArrayList<DetailUserResponse>> = _followingData

    private val _followersData = MutableLiveData<ArrayList<DetailUserResponse>>()
    val followersData: LiveData<ArrayList<DetailUserResponse>> = _followersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getGithubDetail(login: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(login)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listDetailUser.value = response.body()

                    }
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollower(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(login)
        client.enqueue(object : Callback<ArrayList<DetailUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<DetailUserResponse>>,
                response: Response<ArrayList<DetailUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followersData.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<DetailUserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getFollowing(login: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(login)
        client.enqueue(object : Callback<ArrayList<DetailUserResponse>>{
            override fun onResponse(
                call: Call<ArrayList<DetailUserResponse>>,
                response: Response<ArrayList<DetailUserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followingData.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<DetailUserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }


}
package com.example.githubapi.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi.data.database.UserEntity
import com.example.githubapi.data.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavorites(): LiveData<List<UserEntity>> = mUserRepository.getFavoriteUser()

    fun getFavoriteUserByUsername(username:String): LiveData<String> = mUserRepository.getFavoriteUserByUsername(username)
    fun insert(user: UserEntity) {
        mUserRepository.insert(user)
    }
    fun delete(user: UserEntity) {
        mUserRepository.delete(user)
    }
}
package com.example.githubapi.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapi.data.database.UserDao
import com.example.githubapi.data.database.UserEntity
import com.example.githubapi.data.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()



    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getFavoriteUser(): LiveData<List<UserEntity>> = mUserDao.getFavoriteUser()
    fun getFavoriteUserByUsername(username:String): LiveData<String> = mUserDao.getFavoriteUserByUsername(username)

    fun insert(user: UserEntity) {
        executorService.execute { mUserDao.insert(user) }
    }
    fun delete(user: UserEntity) {
        executorService.execute { mUserDao.delete(user) }
    }

}
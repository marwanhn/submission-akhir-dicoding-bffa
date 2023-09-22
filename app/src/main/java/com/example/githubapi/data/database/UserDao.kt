package com.example.githubapi.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)
    @Delete
    fun delete(user: UserEntity)
    @Query("SELECT login FROM UserEntity WHERE login = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<String>

    @Query("SELECT * from UserEntity ORDER BY login ASC")
    fun getFavoriteUser(): LiveData<List<UserEntity>>
}
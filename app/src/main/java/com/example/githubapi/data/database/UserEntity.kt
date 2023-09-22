package com.example.githubapi.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "avatar_url")
    val avatar: String,

) : Parcelable
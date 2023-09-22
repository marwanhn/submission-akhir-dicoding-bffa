package com.example.githubapi.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubapi.data.database.UserEntity

class UserDiffCallback(private val oldUserList: List<UserEntity>, private val newUserList: List<UserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldUserList.size

    override fun getNewListSize(): Int = newUserList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

}
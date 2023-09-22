package com.example.githubapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapi.data.database.UserEntity
import com.example.githubapi.data.response.DetailUserResponse
import com.example.githubapi.databinding.ItemRowUsersBinding
import com.example.githubapi.ui.activity.DetailUserActivity

class FollowPagerAdapter(private val listUser: ArrayList<DetailUserResponse>) : RecyclerView.Adapter<FollowPagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DetailUserResponse) {
            with(binding) {
                Glide.with(root.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(ivImage)
                tvUsername.text = user.login
                tvUrl.text = user.htmlUrl
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(
                    DetailUserActivity.EXTRA_USER, UserEntity(login = user.login, avatar = user.avatarUrl))
                itemView.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}
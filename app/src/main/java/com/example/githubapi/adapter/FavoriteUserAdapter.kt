package com.example.githubapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapi.data.database.UserEntity
import com.example.githubapi.databinding.ItemRowUsersBinding
import com.example.githubapi.ui.activity.DetailUserActivity

class FavoriteUserAdapter(private val listUser: List<UserEntity>) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            with(binding) {
                Glide.with(root.context)
                    .load(user.avatar)
                    .circleCrop()
                    .into(ivImage)
                tvUsername.text = user.login
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, UserEntity(login = user.login, avatar = user.avatar))
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
        viewHolder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }


}
package com.example.githubapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapi.data.response.GithubUser
import com.example.githubapi.databinding.ItemRowUsersBinding

class SearchUserAdapter(private val listUser: List<GithubUser>) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ViewHolder(private val binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            with(binding) {
                Glide.with(root.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(ivImage)
                tvUsername.text = user.login
                tvUrl.text = user.htmlUrl
                root.setOnClickListener { onItemClickCallback.onItemClicked(user) }
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

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}

package com.example.githubapi.data.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(


	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,
)

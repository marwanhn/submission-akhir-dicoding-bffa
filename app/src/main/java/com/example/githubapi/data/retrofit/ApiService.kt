package com.example.githubapi.data.retrofit

import com.example.githubapi.data.response.DetailUserResponse
import com.example.githubapi.data.response.GithubUser
import com.example.githubapi.data.response.UserResponse
import retrofit2.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>


    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<GithubUser>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String?
    ): Call<GithubUser>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String?
    ): Call<ArrayList<GithubUser>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username")
        username: String?
    ): Call<ArrayList<GithubUser>>

}
package com.example.githubapi.data.retrofit

import com.example.githubapi.data.response.DetailUserResponse
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
    @Headers("Authorization: token ")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username")
        login: String?
    ): Call<ArrayList<DetailUserResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username")
        login: String?
    ): Call<ArrayList<DetailUserResponse>>

}
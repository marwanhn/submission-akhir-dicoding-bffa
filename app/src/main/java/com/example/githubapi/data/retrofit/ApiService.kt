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
    @Headers("Authorization: token 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")
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
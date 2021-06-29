package com.paypal.redditop.data.network

import com.paypal.redditop.data.network.dtos.BaseResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("/top.json")
    suspend fun getTopPosts(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int = 25
    ): BaseResponseDto
}
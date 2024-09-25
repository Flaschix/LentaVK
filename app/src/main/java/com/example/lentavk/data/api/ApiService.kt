package com.example.lentavk.data.api

import com.example.testimcompose.data.model.CommentsResponseDto
import com.example.testimcompose.data.model.LikesCountResponseDto
import com.example.testimcompose.data.model.PostResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendation(
        @Query("access_token") token: String
    ): PostResponseDto

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendation(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): PostResponseDto

    @GET("likes.add?v=5.131&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") owner_id: Long,
        @Query("item_id") postId: Long,
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.131&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") owner_id: Long,
        @Query("item_id") postId: Long,
    ): LikesCountResponseDto

    @GET("wall.getComments?v=5.131&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") owner_id: Long,
        @Query("post_id") postId: Long,
    ): CommentsResponseDto
}
package com.example.lentavk.data.mapper

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.PostComment
import com.example.lentavk.domain.entity.Statistic
import com.example.lentavk.domain.entity.StatisticType
import com.example.testimcompose.data.model.CommentsResponseDto
import com.example.testimcompose.data.model.PostResponseDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue

class PostMapper @Inject constructor(){

    fun mapResponseToPost(responseDto: PostResponseDto): List<Post>{
        val result = mutableListOf<Post>()

        val posts = responseDto.postContent.posts
        val groups = responseDto.postContent.groups

        for(post in posts){
            val group = groups.find {
                it.id == post.communityId.absoluteValue
            } ?: break

            val newItem = Post(
                id = post.id,
                groupId = post.communityId,
                groupName = group.name,
                time = mapTimestampToDate(post.date),
                imgGroupUrl = group.imgUrl,
                text = post.text,
                imgContentUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    Statistic(type = StatisticType.LIKES, post.likes.count),
                    Statistic(type = StatisticType.VIEWS, post.views.count),
                    Statistic(type = StatisticType.SHARES, post.reposts.count),
                    Statistic(type = StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLikes > 0
            )

            result.add(newItem)
        }

        return result
    }

    fun mapResponseToComments(response: CommentsResponseDto): List<PostComment>{
        val result = mutableListOf<PostComment>()
        val comments = response.content.comments
        val profiles = response.content.profiles

        for(comment in comments){
            if(comment.text.isBlank()) continue

            val author = profiles.firstOrNull { it.id == comment.authorId } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstName} ${author.lastName}",
                avatarUrl = author.avatarURL,
                text = comment.text,
                date = mapTimestampToDate(comment.date)
            )
            result.add(postComment)
        }

        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String{
        val date = Date(timestamp * 1000)

        return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}
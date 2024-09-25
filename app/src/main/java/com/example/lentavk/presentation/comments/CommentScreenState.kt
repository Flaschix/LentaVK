package com.example.lentavk.presentation.comments

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.PostComment

sealed class CommentScreenState {

    data object Init: CommentScreenState()

    data class Comments(val post: Post, val comments: List<PostComment>): CommentScreenState()

}
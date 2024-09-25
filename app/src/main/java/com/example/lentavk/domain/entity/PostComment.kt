package com.example.lentavk.domain.entity

data class PostComment(
    val id: Long,
    val authorName: String,
    val avatarUrl: String,
    val text: String,
    val date: String
)
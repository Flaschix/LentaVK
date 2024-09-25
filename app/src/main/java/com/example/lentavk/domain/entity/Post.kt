package com.example.lentavk.domain.entity

data class Post(
    val id: Long,
    val groupId: Long,
    val groupName: String,
    val imgGroupUrl: String,
    val imgContentUrl: String?,
    val text: String,
    val time: String,
    val statistics: List<Statistic>,
    val isLiked: Boolean
)
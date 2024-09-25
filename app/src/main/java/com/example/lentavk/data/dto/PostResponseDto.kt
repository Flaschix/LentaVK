package com.example.testimcompose.data.model

import com.google.gson.annotations.SerializedName

data class PostResponseDto(
    @SerializedName("response") val postContent: PostContentDto
)

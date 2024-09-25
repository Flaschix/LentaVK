package com.example.testimcompose.data.model

import com.google.gson.annotations.SerializedName

data class PostContentDto(
    @SerializedName("items") val posts: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
    @SerializedName("next_from") val nexFrom: String?
)

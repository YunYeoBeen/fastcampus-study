package com.fastcampus.fastcampusstudy.post.dto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
)
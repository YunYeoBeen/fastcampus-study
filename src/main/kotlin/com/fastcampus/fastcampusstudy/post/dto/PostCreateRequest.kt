package com.fastcampus.fastcampusstudy.post.dto

data class PostCreateRequest(
    val title : String,
    val content: String,
    val createdBy : String
)

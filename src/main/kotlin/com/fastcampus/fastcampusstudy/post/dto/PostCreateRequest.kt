package com.fastcampus.fastcampusstudy.post.dto

import com.fastcampus.fastcampusstudy.post.domain.PostEntity

data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun PostCreateRequest.toEntity() = PostEntity(
    title = this.title,
    content = this.content,
    createdBy = this.createdBy
)

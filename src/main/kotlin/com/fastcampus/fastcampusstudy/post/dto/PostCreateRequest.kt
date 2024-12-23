package com.fastcampus.fastcampusstudy.post.dto

import com.fastcampus.fastcampusstudy.post.domain.Post
import jakarta.validation.constraints.NotBlank

data class PostCreateRequest(
    @NotBlank
    val title: String,

    @NotBlank
    val content: String,

    @NotBlank
    val createdBy: String
)

fun PostCreateRequest.toEntity() = Post(
    title = this.title,
    content = this.content,
    createdBy = this.createdBy
)

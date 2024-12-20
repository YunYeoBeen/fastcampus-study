package com.fastcampus.fastcampusstudy.post.dto

import com.fastcampus.fastcampusstudy.post.domain.PostEntity
import jakarta.validation.constraints.NotBlank

data class PostCreateRequest(
    @NotBlank
    val title: String,

    @NotBlank
    val content: String,

    @NotBlank
    val createdBy: String
)

fun PostCreateRequest.toEntity() = PostEntity(
    title = this.title,
    content = this.content,
    createdBy = this.createdBy
)

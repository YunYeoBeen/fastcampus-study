package com.fastcampus.fastcampusstudy.post.dto

import jakarta.validation.constraints.NotBlank

data class PostIdResponseDto(
    @NotBlank
    val postId: Long
)

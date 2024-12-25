package com.fastcampus.fastcampusstudy.post.dto

import com.fastcampus.fastcampusstudy.post.domain.Post
import java.time.LocalDateTime

data class PostUpdateResponse(
    val title: String,
    val content: String,
    val createdBy: String,
    val updatedBy: String,
    val modDtm: LocalDateTime,
)

fun PostUpdateResponse.fromPostEntity(post: Post) = PostUpdateResponse(
    title = post.title,
    content = post.content,
    createdBy = post.createdBy,
    updatedBy = post.updateBy!!,
    modDtm = post.modDtm!!
)

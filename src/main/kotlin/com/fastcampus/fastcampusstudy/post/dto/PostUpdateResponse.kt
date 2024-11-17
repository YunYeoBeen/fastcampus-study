package com.fastcampus.fastcampusstudy.post.dto

import com.fastcampus.fastcampusstudy.post.domain.PostEntity
import java.time.LocalDateTime

data class PostUpdateResponse(
    val title: String,
    val content: String,
    val createdBy: String,
    val updatedBy: String,
    val modDtm: LocalDateTime,
)

fun PostUpdateResponse.fromPostEntity(postEntity: PostEntity) = PostUpdateResponse(
    title = postEntity.title,
    content = postEntity.content,
    createdBy = postEntity.createdBy,
    updatedBy = postEntity.updateBy!!,
    modDtm = postEntity.modDtm!!
)

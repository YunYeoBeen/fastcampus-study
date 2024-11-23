package com.fastcampus.fastcampusstudy.post.domain

import com.fastcampus.fastcampusstudy.common.domain.BaseEntity
import com.fastcampus.fastcampusstudy.common.domain.exception.BadRequestException
import com.fastcampus.fastcampusstudy.post.dto.PostResponse
import com.fastcampus.fastcampusstudy.post.dto.PostUpdateRequest
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class PostEntity(
    title: String,
    content: String,
    createdBy: String,
) : BaseEntity(createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ? = null

    var title: String = title

    var content: String = content

    fun update(postUpdateRequest: PostUpdateRequest) {
        if (postUpdateRequest.updatedBy != this.createdBy) {
            throw BadRequestException("author not match")
        }
        this.title = postUpdateRequest.title
        this.content = postUpdateRequest.content
        super.update(postUpdateRequest.updatedBy)
    }

    fun fromEntity(entity: PostEntity): PostResponse = PostResponse(
        title = this.title,
        content = this.content,
        createdBy = entity.createdBy
    )
}

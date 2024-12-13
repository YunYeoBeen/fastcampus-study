package com.fastcampus.fastcampusstudy.post.domain

import com.fastcampus.fastcampusstudy.common.domain.BaseEntity
import com.fastcampus.fastcampusstudy.common.exception.BadRequestException
import com.fastcampus.fastcampusstudy.post.dto.PostResponse
import com.fastcampus.fastcampusstudy.post.dto.PostUpdateRequest
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class PostEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        var title: String,

        var content: String,

        val createdBy: String,

        var updateBy: String? = null
) : BaseEntity() {
        fun update(postEntity: PostEntity, postUpdateRequest: PostUpdateRequest) {
            if (postUpdateRequest.updatedBy != this.createdBy) {
                throw BadRequestException("author not match")
            }
            postEntity.title = postUpdateRequest.title
            postEntity.content = postUpdateRequest.content
            postEntity.updateBy = postUpdateRequest.updatedBy
        }

        fun fromEntity(entity: PostEntity): PostResponse = PostResponse(
                title = entity.title,
                content = entity.content,
                createdBy = entity.createdBy
        )
}

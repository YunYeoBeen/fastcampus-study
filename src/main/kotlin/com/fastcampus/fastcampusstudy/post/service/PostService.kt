package com.fastcampus.fastcampusstudy.post.service

import com.fastcampus.fastcampusstudy.common.domain.exception.ResourceNotFoundException
import com.fastcampus.fastcampusstudy.post.dto.*
import com.fastcampus.fastcampusstudy.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional
    fun savePosts(postCreateRequest: PostCreateRequest): Long {
        return postRepository.save(postCreateRequest.toEntity()).id ?: throw InternalError("저장을 실패했습니다.")
    }

    @Transactional
    fun updatePosts(postUpdateRequest: PostUpdateRequest, id: Long): Long {
        val post = postRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        post.update(postUpdateRequest)
        return id
    }

    @Transactional
    fun deletePosts(id: Long) {
        val post = postRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        postRepository.delete(post)
    }
}

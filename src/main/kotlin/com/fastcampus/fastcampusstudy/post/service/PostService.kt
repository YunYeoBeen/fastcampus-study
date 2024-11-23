package com.fastcampus.fastcampusstudy.post.service

import com.fastcampus.fastcampusstudy.common.domain.exception.BadRequestException
import com.fastcampus.fastcampusstudy.common.domain.exception.ResourceNotFoundException
import com.fastcampus.fastcampusstudy.post.domain.PostEntity
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
    //TODO interceptor에서 로그인 체크하고 오자.

    fun findPost(postId: Long) : PostEntity {
        return postRepository.findByIdOrNull(postId) ?: throw ResourceNotFoundException("게시글을 찾을 없습니다.")
    }

    fun findAllPost(memberId : Long) = postRepository.findAll()

    @Transactional
    fun savePosts(postCreateRequest: PostCreateRequest): Long {
        return postRepository.save(postCreateRequest.toEntity()).id ?: throw InternalError("저장을 실패했습니다.")
    }

    @Transactional
    fun updatePost(postUpdateRequest: PostUpdateRequest, id: Long): Long {
        postRepository.findByIdOrNull(id)?.update(postUpdateRequest) ?: throw ResourceNotFoundException()
        return id
    }

    @Transactional
    fun deletePost(id: Long, createdBy : String) : Long {
        val post = postRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException()
        if(createdBy != post.createdBy) throw BadRequestException("삭제에 실패했습니다.")
        postRepository.delete(post)
        return id
    }
}

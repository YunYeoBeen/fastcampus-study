package com.fastcampus.fastcampusstudy.post

import com.fastcampus.fastcampusstudy.post.dto.PostCreateRequest
import com.fastcampus.fastcampusstudy.post.dto.PostIdResponseDto
import com.fastcampus.fastcampusstudy.post.dto.PostResponse
import com.fastcampus.fastcampusstudy.post.dto.PostUpdateRequest
import com.fastcampus.fastcampusstudy.post.service.PostService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
) {

    @PostMapping("/users")
    fun savePost(
        @Valid @RequestBody
        postCreateRequest: PostCreateRequest,
    ): ResponseEntity<PostResponse> {
        val post = postService.findPost(postService.savePosts(postCreateRequest)).let {
            it.fromEntity(it)
        }
        return ResponseEntity.ok().body(post)
    }

    @PutMapping("/users/{postId}")
    fun updatePost(
        @Valid @RequestBody
        postUpdateRequest: PostUpdateRequest,
        @PathVariable("postId") postId: Long,
    ): ResponseEntity<PostResponse> {
        val post = postService.findPost(postService.updatePost(postUpdateRequest, postId)).let {
            it.fromEntity(it)
        }
        return ResponseEntity.ok().body(post)
    }

    @PostMapping("/users/{postId}")
    fun deletePost(@PathVariable("postId") postId: Long, createdBy: String): ResponseEntity<PostIdResponseDto> {
        postService.deletePost(postId, createdBy)
        return ResponseEntity.ok().body(PostIdResponseDto(postId = postId))
    }
}

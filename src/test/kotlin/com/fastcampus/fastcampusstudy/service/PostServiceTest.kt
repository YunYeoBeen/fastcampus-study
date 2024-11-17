package com.fastcampus.fastcampusstudy.service

import com.fastcampus.fastcampusstudy.common.domain.exception.BadRequestException
import com.fastcampus.fastcampusstudy.common.domain.exception.ResourceNotFoundException
import com.fastcampus.fastcampusstudy.post.domain.PostEntity
import com.fastcampus.fastcampusstudy.post.dto.PostCreateRequest
import com.fastcampus.fastcampusstudy.post.dto.PostUpdateRequest
import com.fastcampus.fastcampusstudy.post.repository.PostRepository
import com.fastcampus.fastcampusstudy.post.service.PostService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec(
    {
        given("게시글 생성시") {
            When("정상적인 게시글 Request 들어올 시") {
                val postId = postService.savePosts(
                    PostCreateRequest(
                        title = "윤여빈 나와",
                        content = "너 뭐하니",
                        createdBy = "y0824.yun"
                    )
                )
                then("게시글이 정상적으로 확인됨") {
                    postId shouldBeGreaterThan 0L
                    val post = postRepository.findById(postId)
                    post.get().title shouldBeEqual "윤여빈 나와"
                }
            }
        }
        given("게시글 수정 시") {
            val updatedPost = PostUpdateRequest(title = "수정본 안녕", content = "다 나오지마", updatedBy = "y0824.yun")
            val savedPost = postRepository.save(PostEntity(title = "안녕", content = "다 나와", createdBy = "y0824.yun"))
            When("정상적으로 수정 시") {
                val updateId = postService.updatePosts(updatedPost, savedPost.id!!)
                then("게시글이 정상적으로 됐다") {
                    val testPost = postRepository.findById(updateId)
                    testPost.get().title shouldBe updatedPost.title
                    testPost.get().content shouldBe updatedPost.content
                    testPost.get().updateBy shouldBe updatedPost.updatedBy
                }
            }
            When("정상적으로 업데이트가 수행되지 않을 시 - Post를 찾을 수 없을 때") {
                then("customerResourceNotFound 에러를 뱉어낸다.") {
                    shouldThrow<ResourceNotFoundException> { postService.updatePosts(updatedPost, 1009L) }
                }
            }
            When("정상적으로 업데이트가 수행되지 않을 시 - 수정자가 일치하지 않을 때") {
                val wrongRequest = PostUpdateRequest(title = "수정본 안녕", content = "다 나오지마", updatedBy = "y0824")
                then("customerBadRequest 에러를 뱉어낸다.") {
                    shouldThrow<BadRequestException> { postService.updatePosts(wrongRequest, savedPost.id!!) }
                }
            }
        }
    }
)

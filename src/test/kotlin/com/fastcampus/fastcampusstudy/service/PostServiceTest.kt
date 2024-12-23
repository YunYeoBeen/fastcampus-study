package com.fastcampus.fastcampusstudy.service

import com.fastcampus.fastcampusstudy.common.exception.BadRequestException
import com.fastcampus.fastcampusstudy.common.exception.ResourceNotFoundException
import com.fastcampus.fastcampusstudy.post.domain.Post
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
    private val postRepository: PostRepository
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
            val updatedPost = PostUpdateRequest(title = "수정본 안녕", content = "다 나오지마", createdBy = "y0824.yun", updatedBy = "y0824.yun")
            val savedPost = postRepository.save(Post(title = "안녕", content = "다 나와", createdBy = "y0824.yun"))
            val wrongRequest = PostUpdateRequest(title = "수정본 안녕", content = "다 나오지마", createdBy = "y0824.yun", updatedBy = "y0824")
            When("정상적으로 수정 시") {
                val updateId = postService.updatePost(updatedPost, savedPost.id!!)
                then("게시글이 정상적으로 됐다") {
                    val testPost = postRepository.findById(updateId)
                    testPost.get().title shouldBe updatedPost.title
                    testPost.get().content shouldBe updatedPost.content
                    testPost.get().updateBy shouldBe updatedPost.updatedBy
                }
            }
            When("정상적으로 업데이트가 수행되지 않을 시 - Post를 찾을 수 없을 때") {
                then("customerResourceNotFound 에러를 뱉어낸다.") {
                    shouldThrow<ResourceNotFoundException> { postService.updatePost(updatedPost, 1009L) }
                }
            }
            When("정상적으로 업데이트가 수행되지 않을 시 - 수정자가 일치하지 않을 때") {
                then("customerBadRequest 에러를 뱉어낸다.") {
                    shouldThrow<BadRequestException> { postService.updatePost(wrongRequest, savedPost.id!!) }
                }
            }
        }
        given("게시글 삭제 시") {
            val postId = postService.savePosts(
                PostCreateRequest(
                    title = "삭제 실행",
                    content = "삭제가 될겁니다.",
                    createdBy = "y0824.yun"
                )
            )
            val newPost =
                postService.savePosts(PostCreateRequest(title = "하이", content = "컨텐츠 생성", createdBy = "y0824.yun"))
            When("정상적으로 수행 시") {
                val deletePost = postService.deletePost(postId, "y0824.yun")
                then("게시글이 정상적으로 삭제됐다") {
                    deletePost shouldBe postId
                    shouldThrow<ResourceNotFoundException> { postService.findPost(postId) }
                }
            }
            When("정상적으로 수행이 안될 시") {
                then("게시글이 정상적으로 수행되지 않을 때") {
                    shouldThrow<ResourceNotFoundException> { postService.deletePost(postId, "y0123.yun") }
                    shouldThrow<BadRequestException> { postService.deletePost(newPost, "y0123.elr") }
                }
            }
        }
    }
)

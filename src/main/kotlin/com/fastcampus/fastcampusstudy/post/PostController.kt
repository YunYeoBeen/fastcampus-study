package com.fastcampus.fastcampusstudy.post

import com.fastcampus.fastcampusstudy.post.dto.PostCreateRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    @PostMapping("/posts")
    fun savePosts(@RequestBody postCreateRequest: PostCreateRequest) : Int {
        return 1
    }
}

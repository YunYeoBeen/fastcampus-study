package com.fastcampus.fastcampusstudy.post.repository

import com.fastcampus.fastcampusstudy.post.domain.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<PostEntity, Long>

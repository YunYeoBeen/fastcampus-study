package com.fastcampus.fastcampusstudy.post.domain

import com.fastcampus.fastcampusstudy.common.domain.BaseDate
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val content: String,

    ) : BaseDate() {
}

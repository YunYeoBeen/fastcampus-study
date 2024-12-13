package com.fastcampus.fastcampusstudy.user.domain

import com.fastcampus.fastcampusstudy.common.domain.BaseEntity
import jakarta.persistence.*

@Entity
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(nullable = false, unique = true)
        val kakaoId: String,

        val email: String? = null,
        val nickname: String? = null
) : BaseEntity()


package com.fastcampus.fastcampusstudy.redis.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class RefreshToken(
        @Id
        val refreshToken: String,
        val email: String
)
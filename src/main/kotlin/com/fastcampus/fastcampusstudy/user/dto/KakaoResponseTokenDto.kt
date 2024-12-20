package com.fastcampus.fastcampusstudy.user.dto

data class KakaoResponseTokenDto(
        val tokenType: String,
        val accesToken : String,
        val expiresIn : Number,
        val refreshToken: String,
        val refreshTokenExpiresIn: Number
)

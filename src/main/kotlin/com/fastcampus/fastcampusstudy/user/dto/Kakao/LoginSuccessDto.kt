package com.fastcampus.fastcampusstudy.user.dto.Kakao

data class LoginSuccessDto(
        val nickName: String,
        val email: String,
        val accessToken : String,
        val refreshToken : String
)

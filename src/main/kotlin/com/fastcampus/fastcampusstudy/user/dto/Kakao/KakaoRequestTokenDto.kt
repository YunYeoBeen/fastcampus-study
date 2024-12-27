package com.fastcampus.fastcampusstudy.user.dto.Kakao

data class KakaoRequestTokenDto(
    val tokenUri: String,
    val clientId: String,
    val grantType: String,
    val redirectUri: String,
    val code: String,
)

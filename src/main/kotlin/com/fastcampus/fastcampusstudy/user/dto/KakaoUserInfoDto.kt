package com.fastcampus.fastcampusstudy.user.dto

data class KakaoUserInfoDto(
    val id: Long,
    val kakaoAccount: KakaoAccount
)

class KakaoAccount(
    val name: String,
    val email: String,
    val birthDay: String
)

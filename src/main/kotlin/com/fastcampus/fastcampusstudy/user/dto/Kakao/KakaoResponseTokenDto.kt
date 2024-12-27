package com.fastcampus.fastcampusstudy.user.dto.Kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoResponseTokenDto(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("refresh_token") val refreshToken: String,
    @JsonProperty("expires_in") val expiresIn: Long,
    @JsonProperty("refresh_token_expires_in") val refreshTokenExpiresIn: Long,
    @JsonProperty("scope") val scope: String,
)

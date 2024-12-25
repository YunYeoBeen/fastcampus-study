package com.fastcampus.fastcampusstudy.user.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUserInfoDto(
    @JsonProperty("id")val id: Long,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount,
)

@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoAccount(
    @JsonProperty("profile")val profile: Profile,
)

@JsonIgnoreProperties(ignoreUnknown = true)
class Profile(
    @JsonProperty("nickname")val nickName: String,
)

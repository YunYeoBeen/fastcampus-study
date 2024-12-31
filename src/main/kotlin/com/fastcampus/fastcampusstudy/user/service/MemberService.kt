package com.fastcampus.fastcampusstudy.user.service

import com.fastcampus.fastcampusstudy.common.Enum.Jwt
import com.fastcampus.fastcampusstudy.common.exception.KakaoException
import com.fastcampus.fastcampusstudy.common.util.JwtTokenProvider
import com.fastcampus.fastcampusstudy.user.domain.Member
import com.fastcampus.fastcampusstudy.user.dto.Kakao.KakaoResponseTokenDto
import com.fastcampus.fastcampusstudy.user.dto.Kakao.KakaoUserInfoDto
import com.fastcampus.fastcampusstudy.user.dto.MemberResponseDto
import com.fastcampus.fastcampusstudy.user.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
@Transactional(readOnly = true)
class MemberService(
        private val restTemplate: RestTemplate,
        private val objectMapper: ObjectMapper,
        private val memberRepository: MemberRepository,
        private val jwtTokenProvider: JwtTokenProvider
) {
    private val logger = KotlinLogging.logger {}

    @Value("\${kakao.provider.token-uri}")
    lateinit var tokenUri: String

    @Value("\${kakao.registration.client-id}")
    lateinit var clientId: String

    @Value("\${kakao.registration.client-secret}")
    lateinit var clientSecret: String

    @Value("\${kakao.registration.authorization-grant-type}")
    lateinit var grantType: String

    @Value("\${kakao.registration.redirect-uri}")
    lateinit var redirectUri: String

    @Value("\${kakao.provider.user-info-uri}")
    lateinit var userInfoUri: String

    @Transactional
    fun saveUser(code: String): MemberResponseDto {
        val kakaoAccessToken = getAccessToken(code)
        val user = getUserInfo(kakaoAccessToken.accessToken)

        val jwtAccessToken = jwtTokenProvider.generateToken(user.kakaoAccount.email, Jwt.access)
        val jwtRefreshToken = jwtTokenProvider.generateToken(user.kakaoAccount.email, Jwt.refresh)

        return memberRepository.findByKakaoId(user.id)?.let {
            MemberResponseDto.fromEntity(it, jwtAccessToken, jwtRefreshToken)
        } ?: MemberResponseDto.fromEntity(memberRepository.save(Member.toEntity(user)), jwtAccessToken, jwtRefreshToken)
    }


    fun getAccessToken(code: String): KakaoResponseTokenDto {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", grantType)
            add("client_id", clientId)
            add("redirect_uri", redirectUri)
            add("code", code)
            add("client_secret", clientSecret)
        }

        val response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                HttpEntity(body, headers),
                String::class.java
        )

        val tokenDto = objectMapper.readValue(response.body, KakaoResponseTokenDto::class.java)

        return tokenDto ?: throw KakaoException.KakaoInternalServerError("Failed to get access token")
    }

    fun getUserInfo(accessToken: String?): KakaoUserInfoDto {
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer $accessToken")
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                HttpEntity(null, headers),
                String::class.java
        )

        val responseDto = objectMapper.readValue(response.body, KakaoUserInfoDto::class.java)

        return responseDto ?: throw KakaoException.KakaoInternalServerError("Failed to get user info")
    }
}

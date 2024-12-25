package com.fastcampus.fastcampusstudy.user.service

import com.fastcampus.fastcampusstudy.common.exception.KakaoException
import com.fastcampus.fastcampusstudy.user.dto.KakaoResponseTokenDto
import com.fastcampus.fastcampusstudy.user.dto.KakaoUserInfoDto
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
) {
    private val logger = KotlinLogging.logger {}

    @Value("\${spring.security.oauth2.client.provider.kakao.token-uri}")
    lateinit var tokenUri: String

    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    lateinit var clientId: String

    @Value("\${spring.security.oauth2.client.registration.kakao.client-secret}")
    lateinit var clientSecret: String

    @Value("\${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    lateinit var grantType: String

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    lateinit var redirectUri: String

    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    lateinit var userInfoUri: String

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

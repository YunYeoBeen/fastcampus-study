package com.fastcampus.fastcampusstudy.user.service

import com.fastcampus.fastcampusstudy.common.Enum.Social
import com.fastcampus.fastcampusstudy.common.exception.KakaoException
import com.fastcampus.fastcampusstudy.user.domain.Member
import com.fastcampus.fastcampusstudy.user.dto.KakaoRequestTokenDto
import com.fastcampus.fastcampusstudy.user.dto.KakaoResponseTokenDto
import com.fastcampus.fastcampusstudy.user.dto.KakaoUserInfoDto
import com.fastcampus.fastcampusstudy.user.repository.MemberRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
@Transactional(readOnly = true)
class MemberService(
    private val restTemplate: RestTemplate,
    private val memberRepository: MemberRepository
) {
    private val logger = KotlinLogging.logger {}

    @Value("\${spring.security.oauth2.client.provider.kakao.token-uri}")
    lateinit var tokenUri: String

    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    lateinit var clientId: String

    @Value("\${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    lateinit var grantType: String

    @Value("\${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    lateinit var redirectUri: String

    @Value("\${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    lateinit var userInfoUri: String

    @Transactional
    fun saveUser(type: Social, code: String): Member {
        val entity = Member.toEntity(getKakaoUserInfo(getKakaoToken(code).accesToken)).apply {
            this.type = type
        }
        return memberRepository.findByKakaoId(entity.kakaoId)?.let {
            throw IllegalStateException("이미 존재하는 유저")
        } ?: memberRepository.save(entity)
    }

    private fun getKakaoUserInfo(accessToken: String): KakaoUserInfoDto {
        val httpHeaders = HttpHeaders().apply {
            add("Authorization", "Bearer $accessToken")
            add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        }

        val responseDto = restTemplate.exchange(
            userInfoUri,
            HttpMethod.POST,
            HttpEntity<HttpHeaders>(httpHeaders),
            KakaoUserInfoDto::class.java
        )

        return responseDto.body ?: throw KakaoException.KakaoInternalServerError("카카오 유저 정보 조회 실패")
    }

    private fun getKakaoToken(code: String): KakaoResponseTokenDto {
        val kakaoRequestTokenDto = KakaoRequestTokenDto(tokenUri, clientId, grantType, redirectUri, code)
        val httpHeaders = HttpHeaders().apply {
            add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        }

        val responseDto = restTemplate.exchange(
            tokenUri,
            HttpMethod.POST,
            HttpEntity(toMultiValueMap(kakaoRequestTokenDto), httpHeaders),
            KakaoResponseTokenDto::class.java
        )

        return responseDto.body ?: throw KakaoException.KakaoUnauthorizedException("카카오 인증 실패")
    }

    private fun toMultiValueMap(kakaoRequestTokenDto: KakaoRequestTokenDto): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("grant_type", kakaoRequestTokenDto.grantType)
            add("client_id", kakaoRequestTokenDto.clientId)
            add("redirect_uri", kakaoRequestTokenDto.redirectUri)
            add("code", kakaoRequestTokenDto.code )
        }
    }
}

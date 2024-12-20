package com.fastcampus.fastcampusstudy.user.service

import com.fastcampus.fastcampusstudy.user.repository.UserRepository
import com.fastcampus.fastcampusstudy.user.domain.User
import com.fastcampus.fastcampusstudy.user.dto.KakaoRequestTokenDto
import com.fastcampus.fastcampusstudy.user.dto.KakaoResponseTokenDto
import com.fastcampus.fastcampusstudy.user.dto.KakaoUserInfoDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class UserService(
        private val webClient: WebClient,
        private val userRepository: UserRepository
) {
    @Value("\${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    lateinit var apiTokenUri: String

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
    fun saveUser(kakaoUserInfoDto: KakaoUserInfoDto): Mono<User> {
        return userRepository.findByKakaoId(kakaoUserInfoDto.id)
                // TODO .flatMap {  }에 대해서 공부
                .switchIfEmpty(
                        Mono.defer {
                            userRepository.save(User.toEntity(kakaoUserInfoDto))
                        }
                )

    }

    private fun registerKakaoUser(code: String): Mono<User> {
        return getKakaoToken(code)
                .flatMap { accessToken ->
                    getKakaoUserInfo(accessToken)
                }
                .flatMap { kakaoUserInfo ->
                    saveUser(kakaoUserInfo)
                }
    }

    private fun getKakaoUserInfo(accessToken: String): Mono<KakaoUserInfoDto> {
        return webClient.post()
                .uri(userInfoUri)
                .headers {
                    it.set("Authorization", "Bearer$accessToken")
                }
                .retrieve()
                .bodyToMono(KakaoUserInfoDto::class.java)
    }

    private fun getKakaoToken(code: String): Mono<String> {
        val kakaoRequestTokenDto = KakaoRequestTokenDto(tokenUri, clientId, grantType, redirectUri, code)

        return webClient.post().uri(apiTokenUri)
                .bodyValue(kakaoRequestTokenDto)
                .retrieve()
                .bodyToMono(KakaoResponseTokenDto::class.java)
                .map { it.accesToken }
                .doOnError { it.printStackTrace() }
    }


}

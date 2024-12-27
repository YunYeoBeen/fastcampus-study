package com.fastcampus.fastcampusstudy.common.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
        private val principal: String, // 보통 사용자 ID 또는 이메일
        private val credentials: String? = null, // 필요하다면 토큰 자체를 포함
        authorities: Collection<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = false // 초기 상태는 인증되지 않음
    }

    override fun getCredentials(): Any? = credentials

    override fun getPrincipal(): Any = principal
}

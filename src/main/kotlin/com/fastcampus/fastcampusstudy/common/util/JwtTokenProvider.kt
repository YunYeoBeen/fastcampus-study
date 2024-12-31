package com.fastcampus.fastcampusstudy.common.util

import com.fastcampus.fastcampusstudy.common.Enum.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class JwtTokenProvider(
        private val redisTemplate: RedisTemplate<String, Any>
) {
    @Value("\${jwt.secret-key}")
    lateinit var secretKey: String

    @Value("\${jwt.access-token-expiration}")
    var accessExpirationTime: Long = 0

    @Value("\${jwt.refresh-token-expiration}")
    var refresExpirationTime: Long = 0


    private val key by lazy { Keys.hmacShaKeyFor(this.secretKey.toByteArray()) }

    // JWT 생성 메서드
    //TODO  JWT 토큰 유효성 체크하는 API 생성 (유효하면 그대로 refreshToken 반환, 유효하지 않다면 accessToken and refreshToken return)
    fun generateToken(email: String, type: Jwt): String {
        val now = Date()
        val expireDate = Date().apply {
            if (type == Jwt.access) {
                Date(now.time + accessExpirationTime)
            } else {
                Date(now.time + refresExpirationTime)
            }
        }
        return Jwts.builder()
                .setSubject(email) // 토큰에 사용자 ID 설정
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(expireDate) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 비밀키 설정
                .compact() // 최종 토큰 생성
                .also {
                    if (type == Jwt.refresh) {
                        redisTemplate.opsForValue().set(email, it, expireDate.time - now.time, TimeUnit.SECONDS)
                    }
                }
    }

    // JWT에서 사용자 ID 추출 메서드
    fun getUserIdFromToken(token: String): String {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
    }

    // 토큰 유효성 검사 메서드
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false
        }
    }
}
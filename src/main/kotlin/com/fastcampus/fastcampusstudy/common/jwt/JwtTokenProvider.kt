package com.fastcampus.fastcampusstudy.common.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret-key}")
    lateinit var secretKey : String

    @Value("\${jwt.expiration-time}")
    var expirationTime : Long = 0

    private val key by lazy { Keys.hmacShaKeyFor(this.secretKey.toByteArray()) }

    // JWT 생성 메서드
    fun generateToken(userId: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)

        return Jwts.builder()
                .setSubject(userId) // 토큰에 사용자 ID 설정
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(expiryDate) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘과 비밀키 설정
                .compact() // 최종 토큰 생성
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
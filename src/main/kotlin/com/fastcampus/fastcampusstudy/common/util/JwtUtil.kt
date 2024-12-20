package com.fastcampus.fastcampusstudy.common.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value

class JwtUtil {

    @Value("\${jwt.secret-key}")
    lateinit var secretKey: String

    @Value("\${jwt.expiration-time}")
    lateinit var expirationTime: Number

    fun generateToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
//                .setExpiration(Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun validateToken(token: String): String? {
        return try {
            val claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
            claims.body.subject
        } catch (e: Exception) {
            null
        }
    }
}

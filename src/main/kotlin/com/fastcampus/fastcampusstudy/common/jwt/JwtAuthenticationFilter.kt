package com.fastcampus.fastcampusstudy.common.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
        private val jwtProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        val token = getTokenFromRequest(request)?.let {
            if (jwtProvider.validateToken(it)) {

                val userId = jwtProvider.getUserIdFromToken(it)

                // com.fastcampus.fastcampusstudy.common.config.jwt.JwtAuthenticationToken 생성
                val authentication = JwtAuthenticationToken(
                        principal = userId,
                        credentials = it
                ).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                    isAuthenticated = true
                }

                // SecurityContext에 저장
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")?.let { bearerToken ->
            if (bearerToken.startsWith("Bearer ")) {
                bearerToken.substring(7)
            } else null
        }
    }
}

package com.fastcampus.fastcampusstudy.common.config

import com.fastcampus.fastcampusstudy.common.Enum.Social
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http.cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정
            .csrf { it.disable() } // CSRF 비활성화
            .authorizeHttpRequests { auth ->
                auth
                        .requestMatchers("user/login/**").permitAll() // 로그인 관련 엔드포인트 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
            }
            .formLogin { it.disable() } // 폼 로그인 비활성화 (필요 시 활성화 가능)
            .httpBasic { it.disable() } // HTTP Basic 인증 비활성화 (필요 시 활성화 가능
            .build()
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:5173") // 허용할 Origin
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
            allowedHeaders = listOf("*") // 모든 헤더 허용
            allowCredentials = true // 인증 정보 허용
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}

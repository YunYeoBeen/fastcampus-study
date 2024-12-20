package com.fastcampus.fastcampusstudy.common.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http.csrf { it.disable() }
        .headers { it.frameOptions { frameOptions -> frameOptions.sameOrigin() } } // h2 설정
        .authorizeHttpRequests {
            it.requestMatchers("/", "/swagger-ui/**").permitAll() // 인자로 전달된 URL은 모두에게 허용
                .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 콘솔접속은 모두에게 허용
                .anyRequest().authenticated() // 그 이외의 모든 요청은 인증 필요
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // session을 사용하지 않으므로 statelsess
        .build()
}

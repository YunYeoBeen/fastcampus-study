package com.fastcampus.fastcampusstudy.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        registerModule(KotlinModule.Builder().build())
    }
}

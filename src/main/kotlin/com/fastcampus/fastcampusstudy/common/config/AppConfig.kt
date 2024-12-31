package com.fastcampus.fastcampusstudy.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Value("\${spring.data.redis.host}")
    lateinit var redisHost: String

    @Value("\${spring.data.redis.port}")
    lateinit var redisPort: Number

    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory(redisHost, redisPort.toInt())

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        registerModule(KotlinModule.Builder().build())
    }

    @Bean
    fun redisTemplate() = RedisTemplate<String, Any>().apply {
        this.connectionFactory = redisConnectionFactory()
        this.keySerializer = StringRedisSerializer()
        this.valueSerializer = StringRedisSerializer()
    }
}

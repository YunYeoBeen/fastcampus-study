package com.fastcampus.fastcampusstudy.user.repository

import com.fastcampus.fastcampusstudy.user.domain.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findByKakaoId(kakaoId:Long) : Mono<User>
}
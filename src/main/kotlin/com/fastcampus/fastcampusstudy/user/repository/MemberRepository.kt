package com.fastcampus.fastcampusstudy.user.repository

import com.fastcampus.fastcampusstudy.user.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByKakaoId(kakaoId: Long): Member?
}

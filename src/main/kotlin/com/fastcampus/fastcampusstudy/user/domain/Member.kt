package com.fastcampus.fastcampusstudy.user.domain

import com.fastcampus.fastcampusstudy.common.Enum.Social
import com.fastcampus.fastcampusstudy.common.domain.BaseEntity
import com.fastcampus.fastcampusstudy.user.dto.KakaoUserInfoDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var kakaoId: Long,

    var nickName: String? = null,

    var type: Social,
) : BaseEntity() {
    companion object {
        fun toEntity(kakaoUserInfoDto: KakaoUserInfoDto): Member {
            return Member(
                kakaoId = kakaoUserInfoDto.id,
                nickName = kakaoUserInfoDto.kakaoAccount.profile.nickName,
                type = Social.kakao
            )
        }
    }
}

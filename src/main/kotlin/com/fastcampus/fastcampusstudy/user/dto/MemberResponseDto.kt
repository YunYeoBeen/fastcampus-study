package com.fastcampus.fastcampusstudy.user.dto

import com.fastcampus.fastcampusstudy.user.domain.Member

class MemberResponseDto(
        val nickName: String,
        val email : String,
        val newYn : String ?= "Y"
        ){
    companion object {
        fun fromEntity(member: Member) = MemberResponseDto(nickName = member.nickName, email = member.email)
    }
}
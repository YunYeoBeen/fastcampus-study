package com.fastcampus.fastcampusstudy.user.dto

import com.fastcampus.fastcampusstudy.user.domain.Member

class MemberResponseDto(
        val nickName: String,
        val email : String,
        val newYn : String ?= "Y",
        var accessToken : String,
        var refreshToken : String
        ){
    companion object {
        fun fromEntity(member: Member, accessToken: String, refreshToken: String) = MemberResponseDto(nickName = member.nickName, email = member.email, accessToken = accessToken, refreshToken = refreshToken)
    }
}
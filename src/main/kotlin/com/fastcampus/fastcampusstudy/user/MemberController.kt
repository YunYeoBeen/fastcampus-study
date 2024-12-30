package com.fastcampus.fastcampusstudy.user

import com.fastcampus.fastcampusstudy.user.dto.Kakao.KakaoAuthCodeDto
import com.fastcampus.fastcampusstudy.user.dto.Kakao.LoginSuccessDto
import com.fastcampus.fastcampusstudy.user.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class MemberController(
    private val memberService: MemberService,
) {
    @PostMapping("login/{type}")
    fun loginBySocial(
            @PathVariable("type") type: String,
            @RequestBody code: KakaoAuthCodeDto,
    ): ResponseEntity<LoginSuccessDto> {
        val user = memberService.saveUser(code.code)
        return ResponseEntity.ok().body(
            LoginSuccessDto(nickName = user.nickName, email = user.email, accessToken = user.accessToken, refreshToken = user.refreshToken)
        )
    }


}

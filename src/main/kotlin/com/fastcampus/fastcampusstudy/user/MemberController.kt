package com.fastcampus.fastcampusstudy.user

import com.fastcampus.fastcampusstudy.common.Enum.Social
import com.fastcampus.fastcampusstudy.user.dto.LoginSuccessDto
import com.fastcampus.fastcampusstudy.user.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("login/{type}")
    fun loginBySocial(@PathVariable("type") type: String, @RequestBody code: String): ResponseEntity<LoginSuccessDto> {
        memberService.saveUser(Social.valueOf(type), code)
        return ResponseEntity.ok().body(LoginSuccessDto(message = "로그인 성공"))
    }
}

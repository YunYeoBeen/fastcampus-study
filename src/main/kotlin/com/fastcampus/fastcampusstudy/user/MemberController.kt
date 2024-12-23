package com.fastcampus.fastcampusstudy.user

import com.fastcampus.fastcampusstudy.common.Enum.Social
import com.fastcampus.fastcampusstudy.user.dto.LoginSuccessDto
import com.fastcampus.fastcampusstudy.user.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("login/{type}")
    fun loginBySocial(@PathVariable("type") type: Social, code: String): ResponseEntity<LoginSuccessDto> {
        memberService.saveUser(type, code)
        return ResponseEntity.ok().body(LoginSuccessDto(message = "로그인 성공"))
    }
}

// package com.fastcampus.fastcampusstudy.common.extendFunction
//
// import com.fastcampus.fastcampusstudy.user.dto.KakaoRequestTokenDto
// import org.springframework.util.LinkedMultiValueMap
// import org.springframework.util.MultiValueMap
//
// fun KakaoRequestTokenDto.toMultiValueMap(): MultiValueMap<String, String> {
//    return LinkedMultiValueMap<String, String>().apply {
//        add("grant_type", grantType)
//        add("client_id", clientId)
//        add("redirect_uri", redirectUri)
//        add("code", code)
//    }
// }

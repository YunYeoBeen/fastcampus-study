package com.fastcampus.fastcampusstudy.common.domain.dto

import org.springframework.http.HttpStatus

data class ApiError(
    val path: String,
    val message: String,
    val status: HttpStatus,
)

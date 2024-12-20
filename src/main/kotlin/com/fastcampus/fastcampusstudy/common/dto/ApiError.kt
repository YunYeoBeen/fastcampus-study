package com.fastcampus.fastcampusstudy.common.dto

import org.springframework.http.HttpStatus

data class ApiError(
    val error: Boolean ? = false,
    val path: String,
    val message: String,
    val status: HttpStatus
)

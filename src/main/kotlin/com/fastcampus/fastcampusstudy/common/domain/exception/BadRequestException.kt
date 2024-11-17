package com.fastcampus.fastcampusstudy.common.domain.exception

import org.springframework.http.HttpStatus

class BadRequestException(message: String? = HttpStatus.BAD_REQUEST.reasonPhrase) : RuntimeException(message)

package com.fastcampus.fastcampusstudy.common.exception

import org.springframework.http.HttpStatus

class ResourceNotFoundException(message: String? = HttpStatus.NOT_FOUND.reasonPhrase) : RuntimeException(message)

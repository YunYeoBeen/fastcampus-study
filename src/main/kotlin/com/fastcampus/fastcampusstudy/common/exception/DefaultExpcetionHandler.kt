package com.fastcampus.fastcampusstudy.common.exception

import com.fastcampus.fastcampusstudy.common.dto.ApiError
import jakarta.servlet.http.HttpServletRequest
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class DefaultExpcetionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun customBadRequestException(e: Exception, httpServletRequest: HttpServletRequest): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(
                path = httpServletRequest.requestURI,
                message = e.message ?: "fail to create",
                status = HttpStatus.BAD_REQUEST
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun customResourceNotFoundException(e: Exception, httpServletRequest: HttpServletRequest): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(
                path = httpServletRequest.requestURI,
                message = e.message ?: "Not Found",
                status = HttpStatus.NOT_FOUND
            ),
            HttpStatus.NOT_FOUND
        )
    }
}

package com.fastcampus.fastcampusstudy.common.exception

sealed class KakaoException(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    class KakaoUnauthorizedException(message: String) : KakaoException(message)
    class KakaoBadRequestException(message: String) : KakaoException(message)
    class KakaoInternalServerError(message: String) : KakaoException(message)
}

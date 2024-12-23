package com.fastcampus.fastcampusstudy.common.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.SQLException

@Configuration
class H2ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Throws(SQLException::class)
    fun h2TcpServer(): Server {
        return Server.createTcpServer(
            "-tcp", // TCP 서버를 활성화
            "-tcpAllowOthers", // 외부에서 접속 허용
            "-tcpPort",
            "9092" // 사용할 포트 지정
        )
    }
}

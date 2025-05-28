package org.example.otpproject.Config

import org.springframework.web.cors.CorsConfiguration

class Cors: CorsConfiguration() {
    init {
        addAllowedOrigin("*")
        addAllowedMethod("*")
        addAllowedHeader("*")
        allowCredentials = true
    }
}
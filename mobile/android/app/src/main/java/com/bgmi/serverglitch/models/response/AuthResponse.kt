package com.bgmi.serverglitch.models.response

data class AuthResponse(
    val token: String,
    val message: String,
    val userId: Long,
    val username: String
)

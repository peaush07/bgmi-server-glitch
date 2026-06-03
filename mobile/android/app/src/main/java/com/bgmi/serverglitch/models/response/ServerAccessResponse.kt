package com.bgmi.serverglitch.models.response

data class ServerAccessResponse(
    val id: Long,
    val serverName: String,
    val remainingTimeMillis: Long,
    val expiresAt: String,
    val status: String,
    val message: String
)

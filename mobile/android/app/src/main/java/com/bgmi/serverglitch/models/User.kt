package com.bgmi.serverglitch.models

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val role: String
)

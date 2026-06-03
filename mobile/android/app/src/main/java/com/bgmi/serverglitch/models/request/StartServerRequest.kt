package com.bgmi.serverglitch.models.request

data class StartServerRequest(
    val serverName: String,
    val season: String,
    val accessType: String
)

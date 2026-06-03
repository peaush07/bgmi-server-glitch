package com.bgmi.serverglitch.network

import com.bgmi.serverglitch.models.request.LoginRequest
import com.bgmi.serverglitch.models.request.RegisterRequest
import com.bgmi.serverglitch.models.request.StartServerRequest
import com.bgmi.serverglitch.models.response.AuthResponse
import com.bgmi.serverglitch.models.response.ServerAccessResponse
import retrofit2.http.*

interface ApiService {
    // Authentication
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    // Server Access
    @POST("server/start")
    suspend fun startServer(@Body request: StartServerRequest): ServerAccessResponse

    @GET("server/active")
    suspend fun getActiveAccess(): List<ServerAccessResponse>

    @POST("server/{accessId}/stop")
    suspend fun stopServer(@Path("accessId") accessId: Long): Map<String, String>
}

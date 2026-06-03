package com.bgmi.serverglitch.network

import android.content.Context
import com.bgmi.serverglitch.security.AntiDetectionManager
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Stealth API Client with anti-detection features
 */
class StealthApiClient(private val context: Context) {

    private val antiDetectionManager = AntiDetectionManager(context)

    private val stealthInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        // Build stealth headers
        val stealthHeaders = antiDetectionManager.generateStealthHeaders()
        
        // Create new request with obfuscated headers
        val newRequest = originalRequest.newBuilder().apply {
            stealthHeaders.forEach { (key, value) ->
                addHeader(key, value)
            }
            
            // Add anonymous token instead of auth token
            addHeader("X-Session-Token", antiDetectionManager.createAnonymousSessionToken())
            addHeader("X-Device-Fingerprint", antiDetectionManager.getCurrentFingerprint())
            
            // Add random request ID for obfuscation
            addHeader("X-Request-ID", antiDetectionManager.generateRandomFingerprint())
            
            // Remove suspicious headers
            removeHeader("Authorization")
        }.build()

        // Random delay to avoid timing pattern detection
        runBlocking {
            delay(antiDetectionManager.getRandomRequestDelay())
        }

        chain.proceed(newRequest)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(stealthInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    companion object {
        private const val BASE_URL = "http://your-backend-url/api/"
    }
}

private suspend fun runBlocking(block: suspend () -> Unit) {
    block()
}

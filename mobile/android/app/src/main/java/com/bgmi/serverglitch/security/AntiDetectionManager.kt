package com.bgmi.serverglitch.security

import android.content.Context
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * Advanced Anti-Detection & Obfuscation System
 * Prevents game anti-cheat from detecting server access
 */
class AntiDetectionManager(private val context: Context) {

    private val secureRandom = SecureRandom()

    /**
     * Generate random device fingerprint to avoid detection
     */
    fun generateRandomFingerprint(): String {
        val randomBytes = ByteArray(32)
        secureRandom.nextBytes(randomBytes)
        return Base64.encodeToString(randomBytes, Base64.NO_WRAP)
    }

    /**
     * Encrypt sensitive data before sending
     */
    fun encryptData(plainText: String, key: String): String {
        try {
            val cipher = Cipher.getInstance("AES")
            val secretKey = deriveKey(key)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val encryptedData = cipher.doFinal(plainText.toByteArray())
            return Base64.encodeToString(encryptedData, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            return plainText
        }
    }

    /**
     * Decrypt received data
     */
    fun decryptData(encryptedText: String, key: String): String {
        try {
            val cipher = Cipher.getInstance("AES")
            val secretKey = deriveKey(key)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val decodedData = Base64.decode(encryptedText, Base64.NO_WRAP)
            val decryptedData = cipher.doFinal(decodedData)
            return String(decryptedData)
        } catch (e: Exception) {
            e.printStackTrace()
            return encryptedText
        }
    }

    /**
     * Generate random User-Agent to avoid pattern detection
     */
    fun generateRandomUserAgent(): String {
        val userAgents = listOf(
            "Mozilla/5.0 (Linux; Android 12; SM-G950F) AppleWebKit/537.36",
            "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36",
            "Mozilla/5.0 (Linux; Android 13; Xiaomi Mi 11) AppleWebKit/537.36",
            "Mozilla/5.0 (Linux; Android 10; OnePlus 8) AppleWebKit/537.36",
            "Mozilla/5.0 (Linux; Android 12; Samsung) AppleWebKit/537.36"
        )
        return userAgents[secureRandom.nextInt(userAgents.size)]
    }

    /**
     * Generate random MAC address
     */
    fun generateRandomMACAddress(): String {
        val macBytes = ByteArray(6)
        secureRandom.nextBytes(macBytes)
        return macBytes.joinToString(":") { "%02x".format(it) }
    }

    /**
     * Hash sensitive data to avoid pattern matching
     */
    fun hashData(data: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(data.toByteArray())
        return Base64.encodeToString(hashBytes, Base64.NO_WRAP)
    }

    /**
     * Generate random request delay to avoid timing pattern detection
     */
    fun getRandomRequestDelay(): Long {
        return secureRandom.nextLong(500, 2000) // 500ms to 2s
    }

    /**
     * Obfuscate API endpoint
     */
    fun obfuscateEndpoint(endpoint: String): String {
        return Base64.encodeToString(endpoint.toByteArray(), Base64.NO_WRAP)
    }

    /**
     * De-obfuscate endpoint
     */
    fun deobfuscateEndpoint(obfuscated: String): String {
        return String(Base64.decode(obfuscated, Base64.NO_WRAP))
    }

    /**
     * Generate stealth mode headers
     */
    fun generateStealthHeaders(): Map<String, String> {
        return mapOf(
            "User-Agent" to generateRandomUserAgent(),
            "X-Device-ID" to generateRandomFingerprint(),
            "X-Request-ID" to generateRandomFingerprint(),
            "Accept-Language" to "en-US,en;q=0.9",
            "Accept-Encoding" to "gzip, deflate, br",
            "Cache-Control" to "no-cache, no-store, must-revalidate",
            "Pragma" to "no-cache",
            "Expires" to "0"
        )
    }

    /**
     * Create anonymous session token
     */
    fun createAnonymousSessionToken(): String {
        val tokenBytes = ByteArray(64)
        secureRandom.nextBytes(tokenBytes)
        return Base64.encodeToString(tokenBytes, Base64.NO_WRAP)
    }

    /**
     * Derive encryption key from password
     */
    private fun deriveKey(key: String): SecretKey {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val keyBytes = messageDigest.digest(key.toByteArray())
        return SecretKeySpec(keyBytes, 0, 32, 0, "AES")
    }

    /**
     * Rotate device fingerprint periodically
     */
    fun rotateFingerprint(): String {
        val newFingerprint = generateRandomFingerprint()
        val preferences = context.getSharedPreferences("stealth_mode", Context.MODE_PRIVATE)
        preferences.edit().putString("device_fingerprint", newFingerprint).apply()
        return newFingerprint
    }

    /**
     * Get current device fingerprint
     */
    fun getCurrentFingerprint(): String {
        val preferences = context.getSharedPreferences("stealth_mode", Context.MODE_PRIVATE)
        return preferences.getString("device_fingerprint", generateRandomFingerprint()) ?: generateRandomFingerprint()
    }
}

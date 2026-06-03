package com.bgmi.serverglitch.security

import android.content.Context
import timber.log.Timber
import java.time.LocalDateTime

/**
 * Manages stealth mode - hides user activity from detection
 */
class StealthModeManager(private val context: Context) {

    private val antiDetectionManager = AntiDetectionManager(context)
    private val preferences = context.getSharedPreferences("stealth_config", Context.MODE_PRIVATE)

    /**
     * Enable stealth mode
     */
    fun enableStealthMode() {
        preferences.edit().apply {
            putBoolean("stealth_enabled", true)
            putString("stealth_start_time", LocalDateTime.now().toString())
            putString("device_fingerprint", antiDetectionManager.generateRandomFingerprint())
            apply()
        }
        Timber.d("Stealth mode ENABLED")
    }

    /**
     * Disable stealth mode
     */
    fun disableStealthMode() {
        preferences.edit().apply {
            putBoolean("stealth_enabled", false)
            remove("stealth_start_time")
            apply()
        }
        Timber.d("Stealth mode DISABLED")
    }

    /**
     * Check if stealth mode is active
     */
    fun isStealthModeEnabled(): Boolean {
        return preferences.getBoolean("stealth_enabled", false)
    }

    /**
     * Rotate fingerprint periodically
     */
    fun rotateDeviceFingerprint() {
        val newFingerprint = antiDetectionManager.rotateFingerprint()
        Timber.d("Device fingerprint rotated: $newFingerprint")
    }

    /**
     * Clear all tracking data
     */
    fun clearTrackingData() {
        preferences.edit().clear().apply()
        Timber.d("All tracking data cleared")
    }

    /**
     * Get stealth status
     */
    fun getStealthStatus(): StealthStatus {
        return StealthStatus(
            enabled = isStealthModeEnabled(),
            startTime = preferences.getString("stealth_start_time", "Not started"),
            deviceFingerprint = antiDetectionManager.getCurrentFingerprint(),
            lastRotation = preferences.getString("last_rotation", "Never")
        )
    }

    data class StealthStatus(
        val enabled: Boolean,
        val startTime: String,
        val deviceFingerprint: String,
        val lastRotation: String
    )
}

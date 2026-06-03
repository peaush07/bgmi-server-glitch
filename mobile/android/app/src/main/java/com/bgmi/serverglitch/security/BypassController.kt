package com.bgmi.serverglitch.security

import android.content.Context
import timber.log.Timber

/**
 * Master Bypass Controller - Orchestrates all anti-detection features
 */
class BypassController(private val context: Context) {

    private val antiDetectionManager = AntiDetectionManager(context)
    private val stealthModeManager = StealthModeManager(context)
    private val proxyRotationManager = ProxyRotationManager()
    private val deviceFingerprintMasker = DeviceFingerprintMasker(context)
    private val silentLogger = SilentLogger(context)

    private val preferences = context.getSharedPreferences("bypass_config", Context.MODE_PRIVATE)

    /**
     * Initialize bypass system
     */
    fun initializeBypass() {
        silentLogger.hideLogFile()
        stealthModeManager.enableStealthMode()
        silentLogger.logSilently("BYPASS", "Bypass system initialized")
        Timber.d("✓ Bypass system initialized")
    }

    /**
     * Enable full stealth mode (maximum anti-detection)
     */
    fun enableFullStealth() {
        stealthModeManager.enableStealthMode()
        stealthModeManager.rotateDeviceFingerprint()
        silentLogger.logSilently("STEALTH", "Full stealth mode enabled")
        Timber.d("✓ Full stealth mode enabled")
    }

    /**
     * Disable bypass system
     */
    fun disableBypass() {
        stealthModeManager.disableStealthMode()
        stealthModeManager.clearTrackingData()
        silentLogger.logSilently("BYPASS", "Bypass system disabled")
        Timber.d("✓ Bypass system disabled")
    }

    /**
     * Get bypass status
     */
    fun getBypassStatus(): BypassStatus {
        return BypassStatus(
            stealthEnabled = stealthModeManager.isStealthModeEnabled(),
            deviceFingerprint = antiDetectionManager.getCurrentFingerprint(),
            fakeDeviceInfo = deviceFingerprintMasker.getAllFakeDeviceInfo(),
            currentProxy = proxyRotationManager.getNextProxy(),
            antiBanProtection = preferences.getBoolean("anti_ban_enabled", true),
            vpnActive = preferences.getBoolean("vpn_enabled", false)
        )
    }

    /**
     * Rotate all identifiers (maximize safety)
     */
    fun rotateAllIdentifiers() {
        stealthModeManager.rotateDeviceFingerprint()
        antiDetectionManager.rotateFingerprint()
        proxyRotationManager.resetRotation()
        silentLogger.logSilently("ROTATE", "All identifiers rotated")
        Timber.d("✓ All identifiers rotated")
    }

    /**
     * Enable VPN mode (route through VPN)
     */
    fun enableVPN() {
        preferences.edit().putBoolean("vpn_enabled", true).apply()
        silentLogger.logSilently("VPN", "VPN mode enabled")
        Timber.d("✓ VPN mode enabled")
    }

    /**
     * Enable anti-ban protection
     */
    fun enableAntiBanProtection() {
        preferences.edit().putBoolean("anti_ban_enabled", true).apply()
        silentLogger.logSilently("ANTI_BAN", "Anti-ban protection enabled")
        Timber.d("✓ Anti-ban protection enabled")
    }

    /**
     * Clear all traces
     */
    fun clearAllTraces() {
        stealthModeManager.clearTrackingData()
        silentLogger.clearLogs()
        antiDetectionManager.rotateFingerprint()
        silentLogger.logSilently("CLEAR", "All traces cleared")
        Timber.d("✓ All traces cleared")
    }

    data class BypassStatus(
        val stealthEnabled: Boolean,
        val deviceFingerprint: String,
        val fakeDeviceInfo: Map<String, String>,
        val currentProxy: String,
        val antiBanProtection: Boolean,
        val vpnActive: Boolean
    )
}

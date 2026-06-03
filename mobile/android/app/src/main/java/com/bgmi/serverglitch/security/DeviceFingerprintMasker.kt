package com.bgmi.serverglitch.security

import android.content.Context
import android.os.Build
import java.security.MessageDigest
import java.util.*

/**
 * Device fingerprint masking - hides real device identity
 */
class DeviceFingerprintMasker(private val context: Context) {

    /**
     * Get fake device model
     */
    fun getFakeDeviceModel(): String {
        val fakeModels = listOf(
            "SM-G950F", "SM-G960F", "SM-G970F",
            "Pixel 4", "Pixel 5", "Pixel 6",
            "OnePlus 8", "OnePlus 9", "Xiaomi Mi 11"
        )
        return fakeModels.random()
    }

    /**
     * Get fake device manufacturer
     */
    fun getFakeManufacturer(): String {
        val manufacturers = listOf(
            "Samsung", "Google", "OnePlus", "Xiaomi", "Oppo"
        )
        return manufacturers.random()
    }

    /**
     * Get fake Android version
     */
    fun getFakeAndroidVersion(): String {
        val versions = listOf(
            "10.0", "11.0", "12.0", "13.0"
        )
        return versions.random()
    }

    /**
     * Get fake IMEI
     */
    fun getFakeIMEI(): String {
        return generateRandomIMEI()
    }

    /**
     * Get all fake device info
     */
    fun getAllFakeDeviceInfo(): Map<String, String> {
        return mapOf(
            "model" to getFakeDeviceModel(),
            "manufacturer" to getFakeManufacturer(),
            "android_version" to getFakeAndroidVersion(),
            "imei" to getFakeIMEI(),
            "device_id" to generateRandomDeviceId(),
            "serial" to generateRandomSerial()
        )
    }

    private fun generateRandomIMEI(): String {
        val imei = StringBuilder()
        for (i in 0..14) {
            imei.append(Random().nextInt(10))
        }
        return imei.toString()
    }

    private fun generateRandomDeviceId(): String {
        return UUID.randomUUID().toString()
    }

    private fun generateRandomSerial(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..16)
            .map { chars.random() }
            .joinToString("")
    }
}

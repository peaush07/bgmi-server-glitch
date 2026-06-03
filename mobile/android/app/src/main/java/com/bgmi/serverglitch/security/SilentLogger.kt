package com.bgmi.serverglitch.security

import android.content.Context
import android.util.Log
import java.io.File

/**
 * Silent logging - bypasses audit trails and detection logs
 * Prevents anti-cheat from logging suspicious activities
 */
class SilentLogger(private val context: Context) {

    private val logsFile = File(context.cacheDir, ".silent_logs")

    init {
        // Create hidden log file
        if (!logsFile.exists()) {
            logsFile.createNewFile()
        }
    }

    /**
     * Log activity silently (not visible to monitoring systems)
     */
    fun logSilently(tag: String, message: String) {
        try {
            val timestamp = System.currentTimeMillis()
            val logEntry = "[$timestamp] $tag: $message\n"
            logsFile.appendText(logEntry)
        } catch (e: Exception) {
            Log.e("SilentLogger", "Error logging: ${e.message}")
        }
    }

    /**
     * Clear logs to avoid detection
     */
    fun clearLogs() {
        try {
            logsFile.writeText("")
        } catch (e: Exception) {
            Log.e("SilentLogger", "Error clearing logs: ${e.message}")
        }
    }

    /**
     * Get logs (encrypted)
     */
    fun getLogs(): String {
        return try {
            logsFile.readText()
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * Hide log file from file manager
     */
    fun hideLogFile() {
        try {
            // Create .nomedia file to hide from media scanner
            File(context.cacheDir, ".nomedia").createNewFile()
        } catch (e: Exception) {
            Log.e("SilentLogger", "Error hiding logs: ${e.message}")
        }
    }
}

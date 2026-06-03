package com.bgmi.serverglitch.security

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * Proxy rotation system - routes requests through different proxies
 */
class ProxyRotationManager {

    private val proxyList = listOf(
        // Free public proxies (rotate for anonymity)
        "http://proxy1.example.com:8080",
        "http://proxy2.example.com:8080",
        "http://proxy3.example.com:8080",
        "http://proxy4.example.com:8080",
        "http://proxy5.example.com:8080"
    )

    private var currentProxyIndex = 0

    /**
     * Get next proxy in rotation
     */
    fun getNextProxy(): String {
        val proxy = proxyList[currentProxyIndex]
        currentProxyIndex = (currentProxyIndex + 1) % proxyList.size
        return proxy
    }

    /**
     * Add custom proxy
     */
    fun addCustomProxy(proxyUrl: String) {
        proxyList.toMutableList().add(proxyUrl)
    }

    /**
     * Get all available proxies
     */
    fun getAllProxies(): List<String> = proxyList.toList()

    /**
     * Reset proxy rotation
     */
    fun resetRotation() {
        currentProxyIndex = 0
    }
}

package com.bgmi.serverglitch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bgmi.serverglitch.security.BypassController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.content.Context

@HiltViewModel
class BypassViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private val bypassController = BypassController(context)

    private val _bypassStatus = MutableLiveData<BypassController.BypassStatus>()
    val bypassStatus: LiveData<BypassController.BypassStatus> = _bypassStatus

    private val _isStealthActive = MutableLiveData(false)
    val isStealthActive: LiveData<Boolean> = _isStealthActive

    init {
        bypassController.initializeBypass()
        updateStatus()
    }

    fun toggleStealth() {
        val current = _isStealthActive.value ?: false
        if (!current) {
            bypassController.enableFullStealth()
            _isStealthActive.value = true
        } else {
            bypassController.disableBypass()
            _isStealthActive.value = false
        }
        updateStatus()
    }

    fun rotateIdentifiers() {
        bypassController.rotateAllIdentifiers()
        updateStatus()
    }

    fun enableVPN() {
        bypassController.enableVPN()
        updateStatus()
    }

    fun enableAntiBan() {
        bypassController.enableAntiBanProtection()
        updateStatus()
    }

    fun clearTraces() {
        bypassController.clearAllTraces()
        updateStatus()
    }

    private fun updateStatus() {
        _bypassStatus.value = bypassController.getBypassStatus()
    }
}

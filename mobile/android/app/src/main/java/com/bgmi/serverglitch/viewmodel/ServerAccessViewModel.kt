package com.bgmi.serverglitch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bgmi.serverglitch.models.request.StartServerRequest
import com.bgmi.serverglitch.models.response.ServerAccessResponse
import com.bgmi.serverglitch.network.ApiClient
import com.bgmi.serverglitch.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ServerAccessViewModel @Inject constructor() : ViewModel() {

    private val apiService = ApiClient.create(ApiService::class.java)

    private val _serverAccessResult = MutableLiveData<Result<ServerAccessResponse>>()
    val serverAccessResult: LiveData<Result<ServerAccessResponse>> = _serverAccessResult

    private val _activeAccess = MutableLiveData<List<ServerAccessResponse>>()
    val activeAccess: LiveData<List<ServerAccessResponse>> = _activeAccess

    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long> = _remainingTime

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun startServer(serverName: String, season: String, accessType: String = "FREE_TRIAL") {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.startServer(
                    StartServerRequest(serverName, season, accessType)
                )
                _serverAccessResult.value = Result.success(response)
                _remainingTime.value = response.remainingTimeMillis
                startTimer(response.remainingTimeMillis, response.id)
                Timber.d("Server access started: ${response.serverName}")
            } catch (e: Exception) {
                _serverAccessResult.value = Result.failure(e)
                Timber.e("Server start error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getActiveAccess() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getActiveAccess()
                _activeAccess.value = response
                Timber.d("Fetched ${response.size} active accesses")
            } catch (e: Exception) {
                Timber.e("Error fetching active access: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun stopServer(accessId: Long) {
        viewModelScope.launch {
            try {
                apiService.stopServer(accessId)
                Timber.d("Server access stopped: $accessId")
                getActiveAccess()
            } catch (e: Exception) {
                Timber.e("Error stopping server: ${e.message}")
            }
        }
    }

    private fun startTimer(initialTimeMillis: Long, accessId: Long) {
        viewModelScope.launch {
            var remaining = initialTimeMillis
            while (remaining > 0 && isActive) {
                delay(1000)
                remaining -= 1000
                _remainingTime.value = remaining
                Timber.d("Time remaining: ${remaining / 1000} seconds")
            }
            if (remaining <= 0) {
                stopServer(accessId)
                _remainingTime.value = 0
            }
        }
    }
}

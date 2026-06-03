package com.bgmi.serverglitch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bgmi.serverglitch.models.request.LoginRequest
import com.bgmi.serverglitch.models.request.RegisterRequest
import com.bgmi.serverglitch.models.response.AuthResponse
import com.bgmi.serverglitch.network.ApiClient
import com.bgmi.serverglitch.network.ApiConfig
import com.bgmi.serverglitch.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val apiService = ApiClient.create(ApiService::class.java)

    private val _loginResult = MutableLiveData<Result<AuthResponse>>()
    val loginResult: LiveData<Result<AuthResponse>> = _loginResult

    private val _registerResult = MutableLiveData<Result<AuthResponse>>()
    val registerResult: LiveData<Result<AuthResponse>> = _registerResult

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(email, password))
                ApiConfig.authToken = response.token
                _loginResult.value = Result.success(response)
                Timber.d("Login successful: ${response.username}")
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
                Timber.e("Login error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.register(RegisterRequest(username, email, password))
                ApiConfig.authToken = response.token
                _registerResult.value = Result.success(response)
                Timber.d("Registration successful: ${response.username}")
            } catch (e: Exception) {
                _registerResult.value = Result.failure(e)
                Timber.e("Registration error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

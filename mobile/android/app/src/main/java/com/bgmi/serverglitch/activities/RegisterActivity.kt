package com.bgmi.serverglitch.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bgmi.serverglitch.R
import com.bgmi.serverglitch.data.TokenManager
import com.bgmi.serverglitch.databinding.ActivityRegisterBinding
import com.bgmi.serverglitch.network.ApiConfig
import com.bgmi.serverglitch.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        tokenManager = TokenManager(this)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            registerButton.setOnClickListener { performRegister() }
            loginLink.setOnClickListener { navigateToLogin() }
        }
    }

    private fun performRegister() {
        val username = binding.usernameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        if (validateInputs(username, email, password, confirmPassword)) {
            viewModel.register(username, email, password)
        }
    }

    private fun validateInputs(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        binding.apply {
            when {
                username.isEmpty() -> {
                    usernameInput.error = "Username is required"
                    return false
                }
                username.length < 3 -> {
                    usernameInput.error = "Username must be at least 3 characters"
                    return false
                }
                email.isEmpty() -> {
                    emailInput.error = "Email is required"
                    return false
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInput.error = "Invalid email format"
                    return false
                }
                password.isEmpty() -> {
                    passwordInput.error = "Password is required"
                    return false
                }
                password.length < 6 -> {
                    passwordInput.error = "Password must be at least 6 characters"
                    return false
                }
                password != confirmPassword -> {
                    confirmPasswordInput.error = "Passwords do not match"
                    return false
                }
            }
        }
        return true
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(this) { result ->
            result.onSuccess { response ->
                Timber.d("Registration successful: ${response.username}")
                lifecycleScope.launch {
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUser(response.userId, response.username)
                    ApiConfig.authToken = response.token
                    navigateToDashboard()
                }
            }
            result.onFailure { error ->
                Timber.e("Registration failed: ${error.message}")
                showErrorDialog("Registration Failed", error.message ?: "An error occurred")
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.registerButton.isEnabled = !isLoading
            binding.loginLink.isEnabled = !isLoading
            binding.usernameInput.isEnabled = !isLoading
            binding.emailInput.isEnabled = !isLoading
            binding.passwordInput.isEnabled = !isLoading
            binding.confirmPasswordInput.isEnabled = !isLoading
            
            if (isLoading) {
                binding.registerButton.text = "Creating Account..."
            } else {
                binding.registerButton.text = "Register"
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun showErrorDialog(title: String, message: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}

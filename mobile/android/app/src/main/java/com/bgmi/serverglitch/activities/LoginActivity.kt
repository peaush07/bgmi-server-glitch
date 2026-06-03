package com.bgmi.serverglitch.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bgmi.serverglitch.R
import com.bgmi.serverglitch.data.TokenManager
import com.bgmi.serverglitch.databinding.ActivityLoginBinding
import com.bgmi.serverglitch.network.ApiConfig
import com.bgmi.serverglitch.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        tokenManager = TokenManager(this)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            loginButton.setOnClickListener { performLogin() }
            registerLink.setOnClickListener { navigateToRegister() }
        }
    }

    private fun performLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()

        if (validateInputs(email, password)) {
            viewModel.login(email, password)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        when {
            email.isEmpty() -> {
                binding.emailInput.error = "Email is required"
                return false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailInput.error = "Invalid email format"
                return false
            }
            password.isEmpty() -> {
                binding.passwordInput.error = "Password is required"
                return false
            }
            password.length < 6 -> {
                binding.passwordInput.error = "Password must be at least 6 characters"
                return false
            }
        }
        return true
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { result ->
            result.onSuccess { response ->
                Timber.d("Login successful: ${response.username}")
                lifecycleScope.launch {
                    tokenManager.saveToken(response.token)
                    tokenManager.saveUser(response.userId, response.username)
                    ApiConfig.authToken = response.token
                    navigateToDashboard()
                }
            }
            result.onFailure { error ->
                Timber.e("Login failed: ${error.message}")
                showErrorDialog("Login Failed", error.message ?: "An error occurred")
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.loginButton.isEnabled = !isLoading
            binding.registerLink.isEnabled = !isLoading
            binding.emailInput.isEnabled = !isLoading
            binding.passwordInput.isEnabled = !isLoading
            
            if (isLoading) {
                binding.loginButton.text = "Loading..."
            } else {
                binding.loginButton.text = "Login"
            }
        }
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
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

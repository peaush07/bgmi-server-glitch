package com.bgmi.serverglitch.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bgmi.serverglitch.R
import com.bgmi.serverglitch.data.TokenManager
import com.bgmi.serverglitch.databinding.ActivityDashboardBinding
import com.bgmi.serverglitch.network.ApiConfig
import com.bgmi.serverglitch.viewmodel.ServerAccessViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: ServerAccessViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        tokenManager = TokenManager(this)
        setupUI()
        observeViewModel()
        viewModel.getActiveAccess()
    }

    private fun setupUI() {
        // Setup Season Spinner
        val seasons = arrayOf("Season 1", "Season 2", "Season 3", "Season 4")
        val seasonAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, seasons)
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.seasonSpinner.adapter = seasonAdapter

        // Setup Access Type Spinner
        val accessTypes = arrayOf("Free Trial (5 min)", "Premium - 1 Hour", "Premium - 24 Hours")
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, accessTypes)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.accessTypeSpinner.adapter = typeAdapter

        binding.startButton.setOnClickListener { startServer() }
    }

    private fun startServer() {
        val serverName = binding.serverNameInput.text.toString().trim()
        val season = binding.seasonSpinner.selectedItem.toString()

        if (serverName.isEmpty()) {
            binding.serverNameInput.error = "Server name is required"
            return
        }

        viewModel.startServer(serverName, season)
        binding.serverNameInput.text?.clear()
    }

    private fun observeViewModel() {
        viewModel.remainingTime.observe(this) { time ->
            val minutes = time / 60
            val seconds = time % 60
            val timeString = String.format("%02d:%02d", minutes, seconds)
            binding.timerText.text = timeString

            if (time > 0) {
                binding.timerText.visibility = android.view.View.VISIBLE
            } else {
                binding.timerText.visibility = android.view.View.GONE
            }
        }

        viewModel.activeAccess.observe(this) { accessList ->
            if (accessList.isNotEmpty()) {
                val adapter = ServerAccessAdapter(accessList) { accessId ->
                    viewModel.stopServer(accessId)
                }
                binding.activeSessionsList.adapter = adapter
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.startButton.isEnabled = !isLoading
            binding.serverNameInput.isEnabled = !isLoading
            
            if (isLoading) {
                binding.startButton.text = "Starting..."
            } else {
                binding.startButton.text = "Start Server Access"
            }
        }
    }
}

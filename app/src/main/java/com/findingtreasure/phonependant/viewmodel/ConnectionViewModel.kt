package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConnectionViewModel : ViewModel() {
    // State for IP and Port
    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip

    private val _port = MutableStateFlow("")
    val port: StateFlow<String> = _port

    private val _isRememberMe = MutableStateFlow(false)
    val isRememberMe: StateFlow<Boolean> = _isRememberMe

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    // Error states for IP and Port validation
    private val _ipError = MutableStateFlow<String?>(null)
    val ipError: StateFlow<String?> = _ipError

    private val _portError = MutableStateFlow<String?>(null)
    val portError: StateFlow<String?> = _portError

    // Event to handle IP change
    fun onIpChanged(newIp: String) {
        _ip.value = newIp
        _ipError.value = null  // Reset the error when user types
    }

    // Event to handle Port change
    fun onPortChanged(newPort: String) {
        _port.value = newPort
        _portError.value = null  // Reset the error when user types
    }

    // Toggle Remember Me
    fun onRememberMeChanged(isChecked: Boolean) {
        _isRememberMe.value = isChecked
    }

    // Validate and attempt connection
    fun connect() {
        val isIpValid = validateIp(_ip.value)
        val isPortValid = validatePort(_port.value)

        if (isIpValid && isPortValid) {
            _isConnected.value = true
            // Additional connection logic can go here
        } else {
            // Set the error messages based on validation
            if (!isIpValid) _ipError.value = "Invalid IP Address"
            if (!isPortValid) _portError.value = "Invalid Port"
            _isConnected.value = false
        }
    }

    // Simple IP validation (you can extend this based on your requirements)
    private fun validateIp(ip: String): Boolean {
        val ipRegex = Regex("^\\d{1,3}(\\.\\d{1,3}){3}\$")
        return ipRegex.matches(ip)
    }

    // Simple port validation (valid ports range from 1 to 65535)
    private fun validatePort(port: String): Boolean {
        return port.toIntOrNull()?.let { it in 1..65535 } ?: false
    }
}

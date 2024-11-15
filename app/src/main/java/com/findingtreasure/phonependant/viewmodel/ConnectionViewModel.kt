package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.comms.NetworkManager
import com.findingtreasure.phonependant.datastore.ConnectionDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ConnectionViewModel(private val dataStore: ConnectionDataStore) : ViewModel() {

    private val _ip = MutableStateFlow("")
    val ip: StateFlow<String> = _ip
    private val _port = MutableStateFlow("")
    val port: StateFlow<String> = _port
    private val _isRememberMe = MutableStateFlow(false)
    val isRememberMe: StateFlow<Boolean> = _isRememberMe
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected
    private val _ipError = MutableStateFlow<String?>(null)
    val ipError: StateFlow<String?> = _ipError
    private val _portError = MutableStateFlow<String?>(null)
    val portError: StateFlow<String?> = _portError

    // Initialize to check if "Remember Me" is saved
    init {
        viewModelScope.launch {
            _isRememberMe.value = dataStore.isRememberMe.first()
            _ip.value = dataStore.ip.first() ?: ""
            _port.value = dataStore.port.first() ?: ""
            if (_isRememberMe.value && _ip.value.isNotEmpty() && _port.value.isNotEmpty()) {
                connect()
            }
        }
    }

    // Handle IP changes
    fun onIpChanged(newIp: String) {
        _ip.value = newIp
        _ipError.value = null
    }

    // Handle Port changes
    fun onPortChanged(newPort: String) {
        _port.value = newPort
        _portError.value = null
    }

    // Toggle Remember Me
    fun onRememberMeChanged(isChecked: Boolean) {
        _isRememberMe.value = isChecked
    }

    // Connect and save preferences if "Remember Me" is checked
    fun connect() {
        val isIpValid = validateIp(_ip.value)
        val isPortValid = validatePort(_port.value)

        if (isIpValid && isPortValid && !_isConnected.value) {
            viewModelScope.launch {
                NetworkManager.connectToAddress(ip.value, port.value.toInt())
                delay(100)
                //_isConnected.value = NetworkManage r.getSocket() != null
                _isConnected.value = true
                if (_isRememberMe.value) {
                    dataStore.savePreferences(_isRememberMe.value, _ip.value, _port.value)
                }
            }
        } else {
            if (!isIpValid) _ipError.value = "Invalid IP Address"
            if (!isPortValid) _portError.value = "Invalid Port"
        }
    }

    // Function to read data from the socket
//    fun readData(bufferSize: Int = 1024): ByteArray? {
//        return socket?.let { readFromConnection(it, bufferSize) }
//    }

    // Log out functionality (clear preferences)
    fun logout() {
        viewModelScope.launch {
            NetworkManager.disconnect()
//            dataStore.clearPreferences()
            _isConnected.value = false
        }
    }

    private fun validateIp(ip: String): Boolean {
        val ipRegex = Regex("^\\d{1,3}(\\.\\d{1,3}){3}\$")
        return ipRegex.matches(ip)
    }

    private fun validatePort(port: String): Boolean {
        return port.toIntOrNull()?.let { it in 1..65535 } ?: false
    }
}
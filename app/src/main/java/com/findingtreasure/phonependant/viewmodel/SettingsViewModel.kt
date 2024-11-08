package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.phonependant.datastore.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStore: SettingsDataStore) : ViewModel() {

    // Default values
    private val defaultJointSensitivity = (1f / 100f * 3.14f / 30f)
    private val defaultCoordSensitivity = (1f / 100f * 5)
    private val defaultCommandSendHertz = 3

    private val _jointSensitivity = MutableStateFlow(defaultJointSensitivity)
    val jointsensitivity: StateFlow<Float> = _jointSensitivity

    private val _coordSensitivity = MutableStateFlow(defaultCoordSensitivity)
    val coordsensitivity: StateFlow<Float> = _coordSensitivity

    private val _commandSendHertz = MutableStateFlow(defaultCommandSendHertz)
    val commandSendHertz: StateFlow<Int> = _commandSendHertz

    init {
        viewModelScope.launch {
            // Initialize settings from DataStore or use default values
            _jointSensitivity.value = dataStore.jointSensitivity.first() ?: defaultJointSensitivity
            _coordSensitivity.value = dataStore.coordSensitivity.first() ?: defaultCoordSensitivity
            _commandSendHertz.value = dataStore.commandSendHertz.first() ?: defaultCommandSendHertz
        }
    }

    fun saveSettings(jointSensitivity: Float, coordSensitivity: Float, commandSendHertz: Int) {
        viewModelScope.launch {
            // Save both sensitivities and command hertz to DataStore
            dataStore.saveJointSensitivity(jointSensitivity)
            dataStore.saveCoordSensitivity(coordSensitivity)
            dataStore.saveCommandSendHertz(commandSendHertz)
        }
    }
}

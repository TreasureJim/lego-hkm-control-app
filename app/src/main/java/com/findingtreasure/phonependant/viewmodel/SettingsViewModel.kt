package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.phonependant.datastore.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStore: SettingsDataStore) : ViewModel() {
    private val _jointsensitivity = MutableStateFlow((1f / 100f * 3.14f / 30f))
    val jointsensitivity: StateFlow<Float> = _jointsensitivity

    private val _coordsensitivity = MutableStateFlow((1f / 100f * 5))
    val coordsensitivity: StateFlow<Float> = _coordsensitivity

    private val _commandSendHertz = MutableStateFlow(3)
    val commandSendHertz: StateFlow<Int> = _commandSendHertz

    init {
        viewModelScope.launch {
//            _jointsensitivity.value = (1f / 100f * 3.14f / 10f)
//            _coordsensitivity.value = (1f / 100f * 5)
//            _jointsensitivity.value = dataStore.jointSensitivity.first()
//            _coordsensitivity.value = dataStore.coordSensitivity.first()
//            _commandSendHertz.value = dataStore.commandSendHertz.first()
        }
    }

    fun saveSettings(sensitivity: Float, commandSendHertz: Int) {
        viewModelScope.launch {
//            dataStore.saveSensitivity(sensitivity)
            dataStore.saveCommandSendHertz(commandSendHertz)
        }
    }
}

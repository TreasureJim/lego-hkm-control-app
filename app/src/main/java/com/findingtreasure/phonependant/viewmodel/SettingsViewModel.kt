package com.findingtreasure.phonependant.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findingtreasure.phonependant.datastore.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStore: SettingsDataStore) : ViewModel() {
    private val _sensitivity = MutableStateFlow(1f)
    val sensitivity: StateFlow<Float> = _sensitivity

    private val _commandSendHertz = MutableStateFlow(100)
    val commandSendHertz: StateFlow<Int> = _commandSendHertz

    init {
        viewModelScope.launch {
            _sensitivity.value = dataStore.sensitivity.first() ?: 1f
            _commandSendHertz.value = dataStore.commandSendHertz.first() ?: 100
        }
    }

    fun saveSettings(sensitivity: Float, commandSendHertz: Int) {
        viewModelScope.launch {
            dataStore.saveSensitivity(sensitivity)
            dataStore.saveCommandSendHertz(commandSendHertz)
        }
    }
}

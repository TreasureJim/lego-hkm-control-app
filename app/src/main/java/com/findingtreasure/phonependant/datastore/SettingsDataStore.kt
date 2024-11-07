package com.findingtreasure.phonependant.datastore

import android.content.Context
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define the DataStore object
val Context.settingsDataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val SENSITIVITY_KEY = floatPreferencesKey("sensitivity")
    private val COMMAND_SEND_HERTZ_KEY = intPreferencesKey("command_send_hertz")

    // Flow to read preferences
    val sensitivity: Flow<Float> = context.settingsDataStore.data.map { preferences ->
        preferences[SENSITIVITY_KEY] ?: 1f
    }

    val commandSendHertz: Flow<Int> = context.settingsDataStore.data.map { preferences ->
        preferences[COMMAND_SEND_HERTZ_KEY] ?: 100
    }

    // Function to save sensitivity preference
    suspend fun saveSensitivity(newSensitivity: Float) {
        context.settingsDataStore.edit { preferences ->
            preferences[SENSITIVITY_KEY] = newSensitivity
        }
    }

    // Function to save commandSendHertz preference
    suspend fun saveCommandSendHertz(newHertz: Int) {
        context.settingsDataStore.edit { preferences ->
            preferences[COMMAND_SEND_HERTZ_KEY] = newHertz
        }
    }
}

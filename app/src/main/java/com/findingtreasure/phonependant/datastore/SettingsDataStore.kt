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

    private val JOINTSENSITIVITY_KEY = floatPreferencesKey("jointsensitivity")
    private val COORDSENSITIVITY_KEY = floatPreferencesKey("coordsensitivity")
    private val COMMAND_SEND_HERTZ_KEY = intPreferencesKey("command_send_hertz")

    // Flow to read preferences
    val jointSensitivity: Flow<Float> = context.settingsDataStore.data.map { preferences ->
        preferences[JOINTSENSITIVITY_KEY] ?: (1f / 100f * 3.14f / 10f)
    }

    val coordSensitivity: Flow<Float> = context.settingsDataStore.data.map { preferences ->
        preferences[COORDSENSITIVITY_KEY] ?: (1f / 100f * 5)
    }

    val commandSendHertz: Flow<Int> = context.settingsDataStore.data.map { preferences ->
        preferences[COMMAND_SEND_HERTZ_KEY] ?: 5
    }

    // Function to save sensitivity preference
//    suspend fun saveSensitivity(newSensitivity: Float) {
//        context.settingsDataStore.edit { preferences ->
//            preferences[SENSITIVITY_KEY] = newSensitivity
//        }
//    }
//
    // Function to save commandSendHertz preference
    suspend fun saveCommandSendHertz(newHertz: Int) {
        context.settingsDataStore.edit { preferences ->
            preferences[COMMAND_SEND_HERTZ_KEY] = newHertz
        }
    }
}

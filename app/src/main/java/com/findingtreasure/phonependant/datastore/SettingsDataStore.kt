package com.findingtreasure.phonependant.datastore

import android.content.Context
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingsDataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val JOINT_SENSITIVITY_KEY = floatPreferencesKey("joint_sensitivity")
    private val COORD_SENSITIVITY_KEY = floatPreferencesKey("coord_sensitivity")
    private val COMMAND_SEND_HERTZ_KEY = intPreferencesKey("command_send_hertz")

    val jointSensitivity: Flow<Float> = context.settingsDataStore.data.map { preferences ->
        preferences[JOINT_SENSITIVITY_KEY] ?: (1f / 100f * 3.14f / 10f)
    }

    val coordSensitivity: Flow<Float> = context.settingsDataStore.data.map { preferences ->
        preferences[COORD_SENSITIVITY_KEY] ?: (1f / 100f * 5)
    }

    val commandSendHertz: Flow<Int> = context.settingsDataStore.data.map { preferences ->
        preferences[COMMAND_SEND_HERTZ_KEY] ?: 5
    }

    suspend fun saveJointSensitivity(newSensitivity: Float) {
        context.settingsDataStore.edit { preferences ->
            preferences[JOINT_SENSITIVITY_KEY] = newSensitivity
        }
    }

    suspend fun saveCoordSensitivity(coordSensitivity: Float) {
        context.settingsDataStore.edit { preferences ->
            preferences[COORD_SENSITIVITY_KEY] = coordSensitivity
        }
    }

    suspend fun saveCommandSendHertz(newHertz: Int) {
        context.settingsDataStore.edit { preferences ->
            preferences[COMMAND_SEND_HERTZ_KEY] = newHertz
        }
    }
}

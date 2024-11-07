package com.findingtreasure.phonependant.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define the DataStore object
val Context.connectionDataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "connection")

class ConnectionDataStore(private val context: Context) {
    private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
    private val IP_KEY = stringPreferencesKey("ip_address")
    private val PORT_KEY = stringPreferencesKey("port_number")

    // Flow to read preferences
    val isRememberMe: Flow<Boolean> = context.connectionDataStore.data.map { preferences ->
        preferences[REMEMBER_ME_KEY] ?: false
    }

    val ip: Flow<String?> = context.connectionDataStore.data.map { preferences ->
        preferences[IP_KEY]
    }

    val port: Flow<String?> = context.connectionDataStore.data.map { preferences ->
        preferences[PORT_KEY]
    }

    // Function to save user preferences
    suspend fun savePreferences(rememberMe: Boolean, ip: String, port: String) {
        context.connectionDataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = rememberMe
            preferences[IP_KEY] = ip
            preferences[PORT_KEY] = port
        }
    }

    // Function to clear user preferences (for logout)
    suspend fun clearPreferences() {
        context.connectionDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

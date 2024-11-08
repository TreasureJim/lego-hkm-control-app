package com.findingtreasure.phonependant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.findingtreasure.phonependant.model.Position

class JoggingViewModelFactory(
    private val initialPosition: Position,
    private val settings: SettingsViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JoggingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JoggingViewModel(initialPosition, settings) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

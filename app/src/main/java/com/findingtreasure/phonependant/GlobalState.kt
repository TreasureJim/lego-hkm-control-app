package com.findingtreasure.phonependant

import com.findingtreasure.phonependant.model.Position
import kotlinx.coroutines.flow.MutableStateFlow

val _currentPostion = MutableStateFlow(Position(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))
package com.findingtreasure.phonependant

import com.findingtreasure.phonependant.model.Status
import kotlinx.coroutines.flow.MutableStateFlow

val _currentPostion = MutableStateFlow(Status(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))
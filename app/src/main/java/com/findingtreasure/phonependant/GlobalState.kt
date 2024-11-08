package com.findingtreasure.phonependant

import com.findingtreasure.phonependant.model.Position
import kotlinx.coroutines.sync.Mutex

class GlobalState {
    val mutex = Mutex()

    //var currentPosition: Position = Position(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
}
package com.findingtreasure.phonependant.model

data class Position(
    val id: Int,
    val name: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val axis1: Double,
    val axis2: Double,
    val axis3: Double
)
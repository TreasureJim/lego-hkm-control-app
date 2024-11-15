package com.findingtreasure.phonependant.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class Accelerometer(private val context: Context) : SensorEventListener {
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometerSensor: Sensor? = null

    private val gravity = FloatArray(3)  // Gravity vector
    private val linearAcceleration = FloatArray(3)  // Normalized acceleration

    // Callback function to update the UI with accelerometer values
    var onAccelerometerDataChanged: ((FloatArray) -> Unit)? = null

    init {
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    // Start listening to the accelerometer
    fun startListening() {
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    // Stop listening to the accelerometer
    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Raw accelerometer data
            val rawX = it.values[0]
            val rawY = it.values[1]
            val rawZ = it.values[2]

            // Apply a low-pass filter to isolate the force of gravity
            val alpha = 0.8f
            gravity[0] = alpha * gravity[0] + (1 - alpha) * rawX
            gravity[1] = alpha * gravity[1] + (1 - alpha) * rawY
            gravity[2] = alpha * gravity[2] + (1 - alpha) * rawZ

            // Remove gravity contribution to get linear acceleration
            val linearX = rawX - gravity[0]
            val linearY = rawY - gravity[1]
            val linearZ = rawZ - gravity[2]

            // Normalize the acceleration values
            normalizeAccelerometerData(linearX, linearY, linearZ)

            // Pass the normalized data to the callback
            onAccelerometerDataChanged?.invoke(linearAcceleration)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // You can handle sensor accuracy changes here if needed
    }

    // Normalizing accelerometer data to align with the coordinate system (normalized between -100 and 100)
    private fun normalizeAccelerometerData(x: Float, y: Float, z: Float) {
        val magnitude = sqrt(x * x + y * y + z * z)

        if (magnitude > 0) {
            linearAcceleration[0] = (x / magnitude) * 100  // X-axis (left-right)
            linearAcceleration[1] = (y / magnitude) * 100  // Y-axis (front-back)
            linearAcceleration[2] = (z / magnitude) * 100  // Z-axis (up-down)
        }
    }
}

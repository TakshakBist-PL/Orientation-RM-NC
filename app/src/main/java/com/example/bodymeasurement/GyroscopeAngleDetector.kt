package com.example.bodymeasurement

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.bodymeasurement.SensorUtility.calculatePitchAndRollFromRotationMatrix
import com.example.bodymeasurement.Utility.isPortrait

class GyroscopeAngleDetector(
    context: Context,
    private val onAngleDetected: (Int, Pair<Double,Double>, String) -> Unit
): SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    companion object {
        private const val TAG = "GyroscopeAngleDetector"
        private const val DEVICE_VERTICAL = 1
        private const val DEVICE_NOT_VERTICAL = 2
    }

    fun start(){
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop(){
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let {
            if (it.sensor.type == Sensor.TYPE_ROTATION_VECTOR){

                val anglePair = calculatePitchAndRollFromRotationMatrix(
                    sensorEvent = it
                )

                if (isPortrait(anglePair.first, anglePair.second)){
                    Log.d(TAG,"Device is vertical")
                    onAngleDetected(DEVICE_VERTICAL, anglePair, "VERTICAL")
                }
                else{
                    Log.d(TAG,"Device is not vertical")
                    onAngleDetected(DEVICE_NOT_VERTICAL, anglePair, "NOT VERTICAL")
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.d(TAG, "OnAccuracyChanged")
    }
}
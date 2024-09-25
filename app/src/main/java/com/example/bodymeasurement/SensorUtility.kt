package com.example.bodymeasurement

import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
import com.example.bodymeasurement.Utility.decimalFormatter
import kotlin.math.atan2
import kotlin.math.sqrt

object SensorUtility {
    fun calculatePitchAndRollFromQuaternion(
        sensorEvent: SensorEvent
    ): Pair<Double,Double> {

        val quaternion = FloatArray(4)

        SensorManager.getQuaternionFromVector(quaternion, sensorEvent.values)

        val qw = quaternion[0]
        val qx = quaternion[1]
        val qy = quaternion[2]
        val qz = quaternion[3]

        // https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles
        val rollRad = atan2(2 * (qw * qx + qy * qz), 1 - 2 * (qx * qx + qy * qy)).toDouble()
        val pitchRad = 2 * atan2(sqrt(1 + 2 * (qw * qy - qx * qz)).toDouble(), sqrt(1 - 2 * (qw * qy - qx * qz)).toDouble()) - Math.PI / 2

        val pitch = decimalFormatter(Math.toDegrees(pitchRad))
        val roll = decimalFormatter(Math.toDegrees(rollRad))

        Log.d("Quaternion", "pitch: $pitch, roll: $roll")
        return Pair(pitch, roll)
    }

    fun calculatePitchAndRollFromRotationMatrix(
        sensorEvent: SensorEvent
    ): Pair<Double,Double>{

        val rotationMatrix = FloatArray(16)
        val orientationAngles = FloatArray(3)

        SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values)
        SensorManager.getOrientation(rotationMatrix, orientationAngles)


        val pitch =  decimalFormatter(Math.toDegrees(orientationAngles[1].toDouble()))
        val roll = decimalFormatter(Math.toDegrees(orientationAngles[2].toDouble()))

        Log.d("RotationMatrix", "pitch: $pitch, roll: $roll")

        return Pair(pitch,roll)
    }
}
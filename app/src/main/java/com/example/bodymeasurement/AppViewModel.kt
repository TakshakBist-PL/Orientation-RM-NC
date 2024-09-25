package com.example.bodymeasurement

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

class AppViewModel: ViewModel() {

    private lateinit var gyroscope: GyroscopeAngleDetector

    private var _angleState = MutableStateFlow(AngleState())
    val screenState: StateFlow<AngleState>  get() = _angleState.asStateFlow()

    fun initialize(context: Context){
        gyroscope = GyroscopeAngleDetector(context){ angleType, anglePair, angleString ->
            _angleState.value = _angleState.value.copy(
                screenState = angleType,
                angleInfo = "$angleString " +
                        "\n Pitch(rotation around X-axis): ${abs(anglePair.first)} " +
                        "\n Roll(rotation around Y-axis)  : ${abs(anglePair.second)}",
            )
        }
        gyroscope.start()
    }

    override fun onCleared() {
        super.onCleared()
        gyroscope.stop()
    }

    fun startGyroscope() {
        gyroscope.start()
    }

    fun stopGyroscope() {
        gyroscope.stop()
    }

}
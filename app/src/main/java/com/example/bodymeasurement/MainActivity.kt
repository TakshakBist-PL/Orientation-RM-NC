package com.example.bodymeasurement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.bodymeasurement.ui.theme.BodyMeasurementTheme

class MainActivity : ComponentActivity() {

    private val viewModel:AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(this)
        enableEdgeToEdge()
        setContent {
            BodyMeasurementTheme {

                MainScreen(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        viewModel.initialize(this)
        super.onResume()
        viewModel.startGyroscope()
    }

    override fun onPause() {
        viewModel.initialize(this)
        super.onPause()
        viewModel.stopGyroscope()
    }

}

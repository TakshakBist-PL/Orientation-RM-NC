package com.example.bodymeasurement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val angleState = viewModel.screenState.collectAsState()
    val color = when(angleState.value.screenState){
                    1 -> Color.Blue
                    2 -> Color.White
                    else -> Color.Red
                }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = angleState.value.angleInfo, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}
package com.javidev.matrix.ui

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.javidev.matrix.model.charters

@Composable
fun MatrixRain() {
    MatrixChar(char = charters[5])
}

@Composable
fun MatrixChar(char: String) {
    var texColor by remember{ mutableStateOf(Color(0xffcefbe4))}
    
    Text(text = char, color = texColor)
}
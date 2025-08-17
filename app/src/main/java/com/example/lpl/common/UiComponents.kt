package com.example.lpl.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextComponent(type: String, text: String) {
    Row {

        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp),
            text = type,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp),
            text = text.replace("\n", ". "),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable()
fun ShowProgressIndicator(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            color = Color.Blue
        )
    }
}
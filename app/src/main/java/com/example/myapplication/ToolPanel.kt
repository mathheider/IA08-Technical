package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToolPanel(
    brushSize: Float,
    onColorChange: (Color) -> Unit,
    onSizeChange: (Float) -> Unit,
    onClearCanvas: () -> Unit,
    onUndo: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { onColorChange(Color.Black) }) { Text("Black") }
        Button(onClick = { onColorChange(Color.Red) }) { Text("Red") }
        Button(onClick = { onColorChange(Color.Blue) }) { Text("Blue") }
        Slider(
            value = brushSize,
            onValueChange = onSizeChange,
            valueRange = 5f..50f,
            modifier = Modifier.weight(1f)
        )
        Button(onClick = onClearCanvas) { Text("Clear") }
        Button(onClick = onUndo) { Text("Undo") }
    }
}
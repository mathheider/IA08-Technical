package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                DrawingScreen()
            }
        }
    }
}

@Composable
fun DrawingScreen() {
    val strokes = remember { mutableStateListOf<DrawingPath>() }
    var brushColor by remember { mutableStateOf(Color.Black) }
    var brushSize by remember { mutableStateOf(10f) }

    Column(modifier = Modifier.fillMaxSize()) {
        ToolPanel(
            brushSize = brushSize,
            onColorChange = { brushColor = it },
            onSizeChange = { brushSize = it },
            onClearCanvas = { strokes.clear() },
            onUndo = { if (strokes.isNotEmpty()) strokes.removeLast() }
        )
        DrawingCanvas(
            strokes = strokes,
            brushColor = brushColor,
            brushSize = brushSize
        )
    }
}
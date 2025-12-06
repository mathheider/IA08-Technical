package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
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

data class DrawingPath(
    val points: MutableList<Offset> = mutableListOf(),
    val color: Color,
    val strokeWidth: Float
)

@Composable
fun DrawingScreen() {
    val strokes = remember { mutableStateListOf<DrawingPath>() }
    val brushColor = Color.Black
    val brushSize = 10f

    DrawingCanvas(
        strokes = strokes,
        brushColor = brushColor,
        brushSize = brushSize
    )
}

@Composable
fun DrawingCanvas(
    strokes: MutableList<DrawingPath>,
    brushColor: Color,
    brushSize: Float
) {
    var currentPath by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPath = listOf(offset)
                    },
                    onDrag = { change, _ ->
                        currentPath = currentPath + change.position
                    },
                    onDragEnd = {
                        strokes.add(DrawingPath(currentPath.toMutableList(), brushColor, brushSize))
                        currentPath = emptyList()
                    }
                )
            }
    ) {
        strokes.forEach { stroke ->
            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    stroke.points.forEachIndexed { index, offset ->
                        if (index == 0) {
                            moveTo(offset.x, offset.y)
                        } else {
                            lineTo(offset.x, offset.y)
                        }
                    }
                },
                color = stroke.color,
                style = Stroke(
                    width = stroke.strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }
        if (currentPath.isNotEmpty()) {
            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    currentPath.forEachIndexed { index, offset ->
                        if (index == 0) {
                            moveTo(offset.x, offset.y)
                        } else {
                            lineTo(offset.x, offset.y)
                        }
                    }
                },
                color = brushColor,
                style = Stroke(
                    width = brushSize,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}
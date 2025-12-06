package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

data class DrawingPath(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Float
)

@Composable
fun DrawingCanvas(
    strokes: List<DrawingPath>,
    onStrokesChanged: (DrawingPath) -> Unit,
    brushColor: Color,
    brushSize: Float
) {
    var currentPath by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(brushColor, brushSize) { // Relaunch if brush changes
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPath = listOf(offset)
                    },
                    onDrag = { change, _ ->
                        currentPath = currentPath + change.position
                    },
                    onDragEnd = {
                        val newPath = DrawingPath(
                            points = currentPath,
                            color = brushColor,
                            strokeWidth = brushSize
                        )
                        onStrokesChanged(newPath)
                        currentPath = emptyList()
                    }
                )
            }
    ) {
        // Draw all the completed strokes
        strokes.forEach { stroke ->
            drawPath(
                path = Path().apply {
                    stroke.points.forEachIndexed { index, offset ->
                        if (index == 0) moveTo(offset.x, offset.y) else lineTo(offset.x, offset.y)
                    }
                },
                color = stroke.color,
                style = Stroke(width = stroke.strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Draw the current in-progress stroke
        if (currentPath.isNotEmpty()) {
            drawPath(
                path = Path().apply {
                    currentPath.forEachIndexed { index, offset ->
                        if (index == 0) moveTo(offset.x, offset.y) else lineTo(offset.x, offset.y)
                    }
                },
                color = brushColor,
                style = Stroke(width = brushSize, cap = StrokeCap.Round)
            )
        }
    }
}
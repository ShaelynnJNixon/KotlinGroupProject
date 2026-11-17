package com.example.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

private const val BOARD_SIZE = 8

// true represents a white square; index a square as board[row][column].
private val board = Array(BOARD_SIZE) { row ->
    BooleanArray(BOARD_SIZE) { column -> (row + column) % 2 == 0 }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Chess Board",
        state = rememberWindowState(size = DpSize(720.dp, 720.dp)),
    ) {
        MaterialTheme {
            ChessBoardScreen()
        }
    }
}

@Composable
private fun ChessBoardScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
            ) {
                val squareSize = size.width / BOARD_SIZE

                for (row in 0 until BOARD_SIZE) {
                    for (column in 0 until BOARD_SIZE) {
                        drawRect(
                            color = if (board[row][column]) Color.White else Color.Black,
                            topLeft = Offset(
                                x = column * squareSize,
                                y = row * squareSize,
                            ),
                            size = Size(squareSize, squareSize),
                        )
                    }
                }
            }
        }
    }
}

package com.example.chargingstation.activites

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerInput
import java.io.File


class FullscreenImageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imagePath = intent.getStringExtra("image_path")

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    imagePath?.let { path ->
                        val bitmap = remember(path) { BitmapFactory.decodeFile(path) }

                        if (bitmap != null) {
                            FullscreenImageViewer(bitmap = bitmap.asImageBitmap())
                        } else {
                            Text("Failed to load image", modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FullscreenImageViewer(bitmap: androidx.compose.ui.graphics.ImageBitmap) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, rotate ->
                scale *= zoom
                rotation += rotate
                offsetX += pan.x
                offsetY += pan.y
            }
        }
    ) {
        Image(
            bitmap = bitmap,
            contentDescription = "Full Screen Image",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale.coerceIn(1f, 5f)
                    scaleY = scale.coerceIn(1f, 5f)
                    rotationZ = rotation
                    translationX = offsetX
                    translationY = offsetY
                }
        )
        // Optional: Add rotate buttons
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { rotation += 90f }) {
                Text("Rotate ➕")
            }
            Button(onClick = { rotation -= 90f }) {
                Text("Rotate ➖")
            }
        }
    }
}
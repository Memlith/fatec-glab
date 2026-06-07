package com.fatec.glab_mobile.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fatec.glab_mobile.domain.model.Room

data class Rect(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)

val rectsForBloco1 = listOf(
    Rect(1315.5f, 1325.5f, 365f, 440f),
    Rect(1285.0f, 1326.0f, 30.45f, 205f),
    Rect(1254.6f, 1326.0f, 30.45f, 205f),
    Rect(1224.1f, 1326.0f, 30.45f, 205f),
    Rect(1285.0f, 1561.0f, 30.45f, 205f),
    Rect(1254.6f, 1561.0f, 30.45f, 205f),
    Rect(1224.1f, 1561.0f, 30.45f, 205f),
    Rect(1193.7f, 1561.0f, 30.45f, 205f),
    Rect(1163.2f, 1561.0f, 30.45f, 205f),
    Rect(1132.8f, 1561.0f, 30.45f, 205f),
    Rect(1102.3f, 1561.0f, 30.45f, 205f),
    Rect(1071.9f, 1561.0f, 30.45f, 205f),
    Rect(1041.4f, 1561.0f, 30.45f, 205f),
    Rect(1011.0f, 1561.0f, 30.45f, 205f),
    Rect(980.5f, 1561.0f, 30.45f, 205f),
    Rect(980.5f, 1531.0f, 335f, 30f)
)

private const val MAP_COLOR_AVAILABLE = 0x80F6F6F6
private const val MAP_COLOR_SELECTED = 0x806B8DF7.toInt()
private const val MAP_COLOR_STROKE = 0x40EBEBEB
private const val MAP_COLOR_TEXT = 0xFF242426
private const val MAP_COLOR_STAIRS = 0x40D1D5DB

@Composable
fun SalaMap(
    rooms: List<Room>,
    selectedRoomId: String,
    building: Int,
    onRoomSelected: (Room) -> Unit,
    onRoomInfo: (Room) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewBox = when (building) {
        1 -> mapOf("width" to 1740f, "height" to 3020f)
        2 -> mapOf("width" to 1650f, "height" to 2700f)
        3 -> mapOf("width" to 1000f, "height" to 1940f)
        else -> mapOf("width" to 1740f, "height" to 3020f)
    }

    var selectedRoom by remember { mutableStateOf<Room?>(null) }

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pointerInput(rooms) {
                    detectTapGestures { offset ->
                        val scaleX = size.width / viewBox["width"]!!
                        val scaleY = size.height / viewBox["height"]!!
                        val mapScale = minOf(scaleX, scaleY)
                        val offsetXCenter = (size.width - viewBox["width"]!! * mapScale) / 2
                        val offsetYCenter = (size.height - viewBox["height"]!! * mapScale) / 2

                        rooms.forEach { room ->
                            val roomLeft = offsetXCenter + room.x * mapScale
                            val roomTop = offsetYCenter + room.y * mapScale
                            val roomRight = roomLeft + room.width * mapScale
                            val roomBottom = roomTop + room.height * mapScale

                            if (offset.x >= roomLeft && offset.x <= roomRight &&
                                offset.y >= roomTop && offset.y <= roomBottom
                            ) {
                                onRoomSelected(room)
                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {
            val scaleX = size.width / viewBox["width"]!!
            val scaleY = size.height / viewBox["height"]!!
            val mapScale = minOf(scaleX, scaleY)

            val offsetXCenter = (size.width - viewBox["width"]!! * mapScale) / 2
            val offsetYCenter = (size.height - viewBox["height"]!! * mapScale) / 2

            drawIntoCanvas { canvas ->
                rooms.forEach { room ->
                    val x = offsetXCenter + room.x * mapScale
                    val y = offsetYCenter + room.y * mapScale
                    val width = room.width * mapScale
                    val height = room.height * mapScale

                    val isSelected = room.id == selectedRoomId
                    val fillColor = if (isSelected) {
                        Color(MAP_COLOR_SELECTED)
                    } else {
                        Color(MAP_COLOR_AVAILABLE)
                    }

                    drawRoundRect(
                        color = fillColor,
                        topLeft = Offset(x, y),
                        size = Size(width, height),
                        cornerRadius = CornerRadius(70f * mapScale, 70f * mapScale)
                    )

                    drawRoundRect(
                        color = Color(MAP_COLOR_STROKE),
                        topLeft = Offset(x, y),
                        size = Size(width, height),
                        cornerRadius = CornerRadius(70f * mapScale, 70f * mapScale),
                        style = Stroke(width = 10f * mapScale)
                    )

                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#242426")
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 100f * mapScale
                        isFakeBoldText = true
                    }

                    val textY = y + height / 2 + paint.textSize / 3
                    canvas.nativeCanvas.drawText(room.label, x + width / 2, textY, paint)
                }

                if (building == 1) {
                    rectsForBloco1.forEach { rect ->
                        val x = offsetXCenter + rect.x * mapScale
                        val y = offsetYCenter + rect.y * mapScale
                        val width = rect.width * mapScale
                        val height = rect.height * mapScale

                        drawRect(
                            color = Color(MAP_COLOR_STAIRS),
                            topLeft = Offset(x, y),
                            size = Size(width, height)
                        )
                    }

                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#242426")
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 80f * mapScale
                        isFakeBoldText = true
                    }

                    canvas.nativeCanvas.drawText(
                        "Escadas",
                        offsetXCenter + 1500f * mapScale,
                        offsetYCenter + 1550f * mapScale,
                        paint
                    )
                }
            }
        }

        if (selectedRoom != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sala: ${selectedRoom!!.label}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { onRoomInfo(selectedRoom!!) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

private val CircleShape = androidx.compose.foundation.shape.CircleShape

@Composable
private fun Box(
    modifier: Modifier,
    contentAlignment: Alignment,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

@Composable
fun MapLegend(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LegendItem(
            color = Color(MAP_COLOR_AVAILABLE),
            label = "Disponível"
        )
        Spacer(modifier = Modifier.weight(1f))
        LegendItem(
            color = Color(MAP_COLOR_SELECTED),
            label = "Selecionado"
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun LegendItem(
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

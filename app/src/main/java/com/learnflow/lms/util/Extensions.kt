package com.learnflow.lms.util

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Color.Companion.fromHex(hex: String): Color {
    val color = hex.removePrefix("#").toLong(16)
    return Color(
        red = ((color shr 16) and 0xFF) / 255f,
        green = ((color shr 8) and 0xFF) / 255f,
        blue = (color and 0xFF) / 255f,
        alpha = 1f
    )
}

fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.toFormattedDateTime(): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Int.toFormattedDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours > 0) {
        "${hours}h ${minutes}m"
    } else {
        "${minutes}m"
    }
}

fun Float.toPercentageString(): String {
    return "${(this * 100).toInt()}%"
}

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }
}

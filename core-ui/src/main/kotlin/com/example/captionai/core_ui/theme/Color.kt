package com.example.captionai.core_ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Colors
val PrimaryPurple = Color(0xFF7B61FF)
val DeepPurple = Color(0xFF9D4EDD)
val PrimaryCyan = Color(0xFF00C2FF)
val PrimaryPink = Color(0xFFFF4D8D)

// Backgrounds
val BackgroundBlack = Color(0xFF0F0F0F)
val BackgroundDark = Color(0xFF121212)
val BackgroundCard = Color(0xFF1C1C1E)

// Text
val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFFB0B0B0)
val TextSoftGray = Color(0xFF8E8E93)

// Gradients
val PrimaryGradient = Brush.horizontalGradient(
    colors = listOf(PrimaryPurple, DeepPurple)
)

val AI_Gradient = Brush.linearGradient(
    colors = listOf(PrimaryPurple, PrimaryCyan, PrimaryPink)
)

val GlassGradient = Brush.verticalGradient(
    colors = listOf(
        Color.White.copy(alpha = 0.1f),
        Color.White.copy(alpha = 0.05f)
    )
)

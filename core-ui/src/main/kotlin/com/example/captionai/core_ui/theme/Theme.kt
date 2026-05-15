package com.example.captionai.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    secondary = PrimaryCyan,
    tertiary = PrimaryPink,
    background = BackgroundBlack,
    surface = BackgroundDark,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = BackgroundCard,
    onSurfaceVariant = TextGray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    secondary = PrimaryCyan,
    tertiary = PrimaryPink,
    background = Color.White,
    surface = Color(0xFFF5F5F7),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color.White,
    onSurfaceVariant = Color.Gray
)

@Composable
fun CaptionAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

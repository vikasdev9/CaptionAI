package com.example.captionai.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.captionai.core_ui.theme.*

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PrimaryPurple)
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge, color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))
        GradientButton(text = "Retry", onClick = onRetry)
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    brush: Brush = PrimaryGradient
) {
    val bgModifier = if (enabled) {
        Modifier.background(brush = brush)
    } else {
        Modifier.background(color = Color.Gray.copy(alpha = 0.3f))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .then(bgModifier)
            .clickable(enabled = enabled, onClick = onClick)
            .then(if (enabled) Modifier.shadow(8.dp, RoundedCornerShape(16.dp)) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(
                1.dp,
                Brush.verticalGradient(
                    listOf(Color.White.copy(alpha = 0.2f), Color.Transparent)
                ),
                RoundedCornerShape(24.dp)
            ),
        color = Color.White.copy(alpha = 0.05f)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}

@Composable
fun AiFeatureCard(
    title: String,
    icon: ImageVector,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(gradient)
            .clickable(onClick = onClick)
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(22.dp))
                .background(BackgroundCard)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}

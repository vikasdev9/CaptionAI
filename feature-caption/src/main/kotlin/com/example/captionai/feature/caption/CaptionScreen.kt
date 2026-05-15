package com.example.captionai.feature.caption

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.captionai.core.ResultState
import com.example.captionai.core_ui.components.*
import com.example.captionai.core_ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptionScreen(
    onBack: () -> Unit,
    viewModel: CaptionViewModel = hiltViewModel()
) {
    var topic by remember { mutableStateOf("") }
    var selectedTone by remember { mutableStateOf("Funny") }
    val tones = listOf("Funny", "Motivational", "Luxury", "Aesthetic", "Professional", "Friendly")

    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Caption Generator", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Craft the Perfect Post",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "Let AI write captions that stop the scroll.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                label = { Text("What's your post about?") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("e.g., Hiking in the Swiss Alps") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = BackgroundCard,
                    focusedContainerColor = BackgroundCard,
                    unfocusedContainerColor = BackgroundCard,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = PrimaryPurple,
                    unfocusedLabelColor = TextGray
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Choose a Tone",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                tones.forEach { tone ->
                    ToneChip(
                        text = tone,
                        isSelected = selectedTone == tone,
                        onClick = { selectedTone = tone }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            GradientButton(
                text = "Generate Caption",
                onClick = { viewModel.generateCaption(topic, selectedTone) },
                enabled = topic.isNotBlank()
            )

            Spacer(modifier = Modifier.height(32.dp))

            when (val state = uiState) {
                is ResultState.Loading -> LoadingScreen(modifier = Modifier.height(200.dp))
                is ResultState.Success -> {
                    CaptionResultCard(
                        content = state.data,
                        onCopy = { clipboardManager.setText(AnnotatedString(state.data)) },
                        onSave = { viewModel.saveCaption(state.data) }
                    )
                }
                is ResultState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.generateCaption(topic, selectedTone) })
                else -> {}
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ToneChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) PrimaryGradient else Brush.linearGradient(listOf(BackgroundCard, BackgroundCard)))
            .clickable { onClick() },
        color = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (isSelected) Color.White else TextGray,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun CaptionResultCard(
    content: String,
    onCopy: () -> Unit,
    onSave: () -> Unit
) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "AI Result",
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryPurple,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = content, style = MaterialTheme.typography.bodyLarge, color = Color.White, lineHeight = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onCopy) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Copy", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}

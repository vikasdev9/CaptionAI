package com.example.captionai.feature.reels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.VideoLibrary
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
fun ReelsScreen(
    onBack: () -> Unit,
    viewModel: ReelsViewModel = hiltViewModel()
) {
    var niche by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Vlog") }
    val categories = listOf("Vlog", "Tutorial", "Behind the Scenes", "Tips & Tricks", "Trends", "Motivational")

    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Reel Ideas", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
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
                text = "Viral Reel Concepts",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "Generate hooks and ideas that go viral.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = niche,
                onValueChange = { niche = it },
                label = { Text("Enter your niche") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("e.g., Tech Reviews, Cooking") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryCyan,
                    unfocusedBorderColor = BackgroundCard,
                    focusedContainerColor = BackgroundCard,
                    unfocusedContainerColor = BackgroundCard,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = PrimaryCyan,
                    unfocusedLabelColor = TextGray
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Video Category",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                categories.forEach { category ->
                    CategoryChip(
                        text = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            GradientButton(
                text = "Generate Viral Idea",
                onClick = { viewModel.generateReelIdeas(niche, selectedCategory) },
                enabled = niche.isNotBlank(),
                brush = Brush.horizontalGradient(listOf(PrimaryCyan, PrimaryPurple))
            )

            Spacer(modifier = Modifier.height(32.dp))

            when (val state = uiState) {
                is ResultState.Loading -> LoadingScreen(modifier = Modifier.height(200.dp))
                is ResultState.Success -> {
                    ReelIdeaCard(
                        content = state.data,
                        onCopy = { clipboardManager.setText(AnnotatedString(state.data)) },
                        onSave = { viewModel.saveIdea(state.data) }
                    )
                }
                is ResultState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.generateReelIdeas(niche, selectedCategory) })
                else -> {}
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Brush.horizontalGradient(listOf(PrimaryCyan, PrimaryPurple)) else Brush.linearGradient(listOf(BackgroundCard, BackgroundCard)))
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
fun ReelIdeaCard(
    content: String,
    onCopy: () -> Unit,
    onSave: () -> Unit
) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Viral Concept",
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryCyan,
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
                Text("Copy Idea", color = Color.White)
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

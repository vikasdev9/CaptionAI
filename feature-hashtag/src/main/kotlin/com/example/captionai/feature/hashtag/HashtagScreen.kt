package com.example.captionai.feature.hashtag

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun HashtagScreen(
    onBack: () -> Unit,
    viewModel: HashtagViewModel = hiltViewModel()
) {
    var niche by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Hashtag Generator", color = Color.White) },
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
                text = "Boost Your Reach",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "AI will find the best trending hashtags for your niche.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = niche,
                onValueChange = { niche = it },
                label = { Text("Enter niche (e.g., Travel Photography)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = BackgroundCard,
                    focusedContainerColor = BackgroundCard,
                    unfocusedContainerColor = BackgroundCard,
                    focusedLabelColor = PrimaryPurple,
                    unfocusedLabelColor = TextGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            GradientButton(
                text = "Generate AI Hashtags",
                onClick = { viewModel.generateHashtags(niche) },
                enabled = niche.isNotBlank()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Trending Section
            Text(
                text = "Trending Categories",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                TrendingChip("#Fashion")
                TrendingChip("#Tech")
                TrendingChip("#Aesthetic")
                TrendingChip("#Fitness")
            }

            Spacer(modifier = Modifier.height(32.dp))

            when (val state = uiState) {
                is ResultState.Loading -> LoadingScreen(modifier = Modifier.height(200.dp))
                is ResultState.Success -> {
                    HashtagResultCard(
                        content = state.data,
                        onCopy = { clipboardManager.setText(AnnotatedString(state.data)) },
                        onSave = { viewModel.saveHashtags(state.data) }
                    )
                }
                is ResultState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.generateHashtags(niche) })
                else -> {}
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TrendingChip(text: String) {
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = BackgroundCard
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Tag, contentDescription = null, tint = PrimaryCyan, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
fun HashtagResultCard(
    content: String,
    onCopy: () -> Unit,
    onSave: () -> Unit
) {
    GlassCard {
        Text(
            text = "AI Generated Tags",
            style = MaterialTheme.typography.titleSmall,
            color = PrimaryCyan,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = content, style = MaterialTheme.typography.bodyMedium, color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onCopy) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Copy All", color = Color.White)
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

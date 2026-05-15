package com.example.captionai.feature.bio

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
import androidx.compose.material.icons.filled.Person
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
fun BioScreen(
    onBack: () -> Unit,
    viewModel: BioViewModel = hiltViewModel()
) {
    var profession by remember { mutableStateOf("") }
    var selectedStyle by remember { mutableStateOf("Creative") }
    val styles = listOf("Creative", "Minimalist", "Professional", "Witty", "Empowering", "Mysterious")

    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Bio Generator", color = Color.White) },
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
                text = "Personal Branding",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "Create a bio that defines you in seconds.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = profession,
                onValueChange = { profession = it },
                label = { Text("What do you do?") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("e.g., Digital Creator & Foodie") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPink,
                    unfocusedBorderColor = BackgroundCard,
                    focusedContainerColor = BackgroundCard,
                    unfocusedContainerColor = BackgroundCard,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = PrimaryPink,
                    unfocusedLabelColor = TextGray
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Personality Style",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                styles.forEach { style ->
                    StyleChip(
                        text = style,
                        isSelected = selectedStyle == style,
                        onClick = { selectedStyle = style }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            GradientButton(
                text = "Generate My Bio",
                onClick = { viewModel.generateBio(profession, selectedStyle) },
                enabled = profession.isNotBlank(),
                brush = Brush.horizontalGradient(listOf(PrimaryPink, Color(0xFFFF9A8B)))
            )

            Spacer(modifier = Modifier.height(32.dp))

            when (val state = uiState) {
                is ResultState.Loading -> LoadingScreen(modifier = Modifier.height(200.dp))
                is ResultState.Success -> {
                    BioResultCard(
                        content = state.data,
                        onCopy = { clipboardManager.setText(AnnotatedString(state.data)) },
                        onSave = { viewModel.saveBio(state.data) }
                    )
                }
                is ResultState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.generateBio(profession, selectedStyle) })
                else -> {}
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun StyleChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Brush.horizontalGradient(listOf(PrimaryPink, Color(0xFFFF9A8B))) else Brush.linearGradient(listOf(BackgroundCard, BackgroundCard)))
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
fun BioResultCard(
    content: String,
    onCopy: () -> Unit,
    onSave: () -> Unit
) {
    GlassCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Bio Idea",
                style = MaterialTheme.typography.titleSmall,
                color = PrimaryPink,
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

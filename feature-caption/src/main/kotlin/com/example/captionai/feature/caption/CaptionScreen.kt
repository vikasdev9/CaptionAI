package com.example.captionai.feature.caption

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.captionai.core.ResultState
import com.example.captionai.core_ui.components.ErrorScreen
import com.example.captionai.core_ui.components.LoadingScreen
import com.example.captionai.core_ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptionScreen(
    onBack: () -> Unit,
    viewModel: CaptionViewModel = hiltViewModel()
) {
    var topic by remember { mutableStateOf("") }
    var selectedTone by remember { mutableStateOf("Friendly") }
    val tones = listOf("Friendly", "Funny", "Motivational", "Professional", "Luxury", "Aesthetic")
    var expanded by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Caption Generator") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = topic,
                onValueChange = { topic = it },
                label = { Text("What's your post about?") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., A sunset at the beach") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedTone,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Tone") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tones.forEach { tone ->
                        DropdownMenuItem(
                            text = { Text(tone) },
                            onClick = {
                                selectedTone = tone
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Generate Caption",
                onClick = { viewModel.generateCaption(topic, selectedTone) },
                enabled = topic.isNotBlank()
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (val state = uiState) {
                is ResultState.Loading -> LoadingScreen(modifier = Modifier.height(200.dp))
                is ResultState.Success -> {
                    ResultCard(
                        content = state.data,
                        onCopy = { clipboardManager.setText(AnnotatedString(state.data)) },
                        onSave = { viewModel.saveCaption(state.data) }
                    )
                }
                is ResultState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.generateCaption(topic, selectedTone) })
                else -> {}
            }
        }
    }
}

@Composable
fun ResultCard(
    content: String,
    onCopy: () -> Unit,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onCopy) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Copy")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onSave) {
                    Text("Save")
                }
            }
        }
    }
}

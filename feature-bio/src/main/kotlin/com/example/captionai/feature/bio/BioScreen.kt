package com.example.captionai.feature.bio

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
fun BioScreen(
    onBack: () -> Unit,
    viewModel: BioViewModel = hiltViewModel()
) {
    var profession by remember { mutableStateOf("") }
    var personality by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bio Generator") },
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
                value = profession,
                onValueChange = { profession = it },
                label = { Text("Your Profession") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., Software Engineer, Artist") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = personality,
                onValueChange = { personality = it },
                label = { Text("Your Personality / Style") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., Minimalist, Fun-loving, Witty") }
            )
            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Generate Bio",
                onClick = { viewModel.generateBio(profession, personality) },
                enabled = profession.isNotBlank() && personality.isNotBlank()
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (val state = uiState) {
                is ResultState.Loading -> LoadingScreen(modifier = Modifier.height(200.dp))
                is ResultState.Success -> {
                    BioResultCard(
                        content = state.data,
                        onCopy = { clipboardManager.setText(AnnotatedString(state.data)) },
                        onSave = { viewModel.saveBio(state.data) }
                    )
                }
                is ResultState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.generateBio(profession, personality) })
                else -> {}
            }
        }
    }
}

@Composable
fun BioResultCard(
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

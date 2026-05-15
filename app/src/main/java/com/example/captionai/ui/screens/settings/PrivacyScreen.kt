package com.example.captionai.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.captionai.core_ui.components.SettingsNavigationItem
import com.example.captionai.core_ui.components.SettingsSectionCard
import com.example.captionai.core_ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Privacy & Data", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                SettingsSectionCard(title = "Data Management") {
                    SettingsNavigationItem(
                        icon = Icons.Default.Info,
                        title = "What we collect",
                        onClick = { /* Info dialog */ }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsNavigationItem(
                        icon = Icons.Default.Download,
                        title = "Export My Data",
                        onClick = { /* Export logic */ }
                    )
                }
            }

            item {
                SettingsSectionCard(title = "Account Actions") {
                    SettingsNavigationItem(
                        icon = Icons.Default.Delete,
                        title = "Delete Account",
                        onClick = { /* Delete confirmation */ }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Deleting your account will permanently remove all your AI generation history and settings.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

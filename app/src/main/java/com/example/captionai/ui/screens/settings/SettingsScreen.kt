package com.example.captionai.ui.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.captionai.core_ui.components.*
import com.example.captionai.core_ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPremium: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToAuth: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f)) // Glassy effect
            ) {
                TopAppBar(
                    title = { 
                        Text(
                            "Settings", 
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ... items ...
            item { Spacer(modifier = Modifier.height(12.dp)) }

            // PREFERENCES SECTION
            item {
                SettingsSectionCard(title = "Preferences") {
                    SettingsToggleItem(
                        icon = Icons.Default.Notifications,
                        title = "Push Notifications",
                        subtitle = "Reminders & AI suggestions",
                        checked = uiState.isNotificationsEnabled,
                        onCheckedChange = { 
                            viewModel.onEvent(SettingsEvents.ToggleNotifications(it)) 
                        }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsToggleItem(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Mode",
                        subtitle = "Always on for premium contrast",
                        checked = uiState.isDarkMode,
                        onCheckedChange = { viewModel.onEvent(SettingsEvents.ToggleDarkMode(it)) }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsNavigationItem(
                        icon = Icons.Default.Language,
                        title = "Language",
                        trailingText = uiState.selectedLanguage,
                        onClick = { viewModel.onEvent(SettingsEvents.ToggleLanguageBottomSheet) }
                    )
                }
            }

            // ACCOUNT SECTION
            item {
                SettingsSectionCard(title = "Account") {
                    SettingsNavigationItem(
                        icon = Icons.Default.AccountBalanceWallet,
                        title = "Subscription",
                        trailingText = "Pro Trial",
                        onClick = onNavigateToPremium
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsNavigationItem(
                        icon = Icons.Default.Security,
                        title = "Privacy & Data",
                        onClick = onNavigateToPrivacy
                    )
                }
            }

            // ABOUT SECTION
            item {
                SettingsSectionCard(title = "About") {
                    SettingsNavigationItem(
                        icon = Icons.Default.Description,
                        title = "Terms of Service",
                        onClick = { /* Open URL */ }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsNavigationItem(
                        icon = Icons.Default.Policy,
                        title = "Privacy Policy",
                        onClick = { /* Open URL */ }
                    )
                    HorizontalDivider(color = Color.White.copy(alpha = 0.05f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("App Version", color = Color.White, fontWeight = FontWeight.Medium)
                        Text(uiState.appVersion, color = TextGray)
                    }
                }
            }

            // LOGOUT BUTTON
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { 
                        viewModel.onEvent(SettingsEvents.Logout)
                        onNavigateToAuth()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(28.dp),
                    contentPadding = PaddingValues()
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(28.dp),
                        border = BorderStroke(1.dp, PrimaryPink.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = null, tint = PrimaryPink)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Log out", color = PrimaryPink, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Caption AI - v${uiState.appVersion}",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSoftGray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    if (uiState.showLanguageBottomSheet) {
        LanguageBottomSheet(
            selectedLanguage = uiState.selectedLanguage,
            onLanguageSelected = { viewModel.onEvent(SettingsEvents.ChangeLanguage(it)) },
            onDismiss = { viewModel.onEvent(SettingsEvents.ToggleLanguageBottomSheet) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BackgroundDark,
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Select Language",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            val languages = listOf("English", "Hindi", "Arabic", "French")
            languages.forEach { language ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLanguageSelected(language) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(language, color = Color.White)
                    if (language == selectedLanguage) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = PrimaryPurple)
                    }
                }
            }
        }
    }
}

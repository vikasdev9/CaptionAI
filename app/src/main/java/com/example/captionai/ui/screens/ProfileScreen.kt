package com.example.captionai.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.captionai.core_ui.components.*
import com.example.captionai.core_ui.theme.*
import com.example.captionai.ui.screens.profile.ProfileEvents
import com.example.captionai.ui.screens.profile.ProfileUiState
import com.example.captionai.ui.screens.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToInsights: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onEvent(ProfileEvents.UploadImage(it)) }
    }

    val shareApp = {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Join me on Caption AI and create amazing content! https://captionai.com/invite")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = Color.White, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> LoadingScreen()
                is ProfileUiState.Error -> ErrorScreen(message = state.message, onRetry = { viewModel.onEvent(ProfileEvents.LoadProfile) })
                is ProfileUiState.Success -> {
                    val user = state.user
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            // Avatar Section
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clickable { imagePickerLauncher.launch("image/*") },
                                contentAlignment = Alignment.Center
                            ) {
                                // Animated Glowing Border
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(AI_Gradient)
                                        .padding(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .background(BackgroundBlack)
                                    )
                                }
                                
                                AsyncImage(
                                    model = user.profileImageUrl.ifEmpty { "https://via.placeholder.com/150" },
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .size(108.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                
                                // Premium Badge
                                if (user.isPremium) {
                                    Surface(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .size(32.dp)
                                            .clip(CircleShape),
                                        color = PrimaryPink
                                    ) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = user.name,
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${user.handle} • ${if (user.isPremium) "Pro member" else "Free plan"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextGray
                            )
                        }

                        // Stats Section
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                StatsCard(label = "Generations", value = user.stats.generations.toString(), modifier = Modifier.weight(1f))
                                StatsCard(label = "Saved", value = user.stats.saved.toString(), modifier = Modifier.weight(1f))
                                StatsCard(label = "Streak", value = "${user.stats.streak}d", modifier = Modifier.weight(1f))
                            }
                        }

                        // Premium Card
                        item {
                            PremiumSubscriptionCard(
                                trialDaysLeft = user.trialDaysLeft,
                                onManageClick = { /* Navigate to subscription */ }
                            )
                        }

                        // Menu List
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                ProfileMenuItem(icon = Icons.Default.FavoriteBorder, title = "Saved Content", onClick = { /* Navigate to Saved */ })
                                ProfileMenuItem(icon = Icons.Default.BarChart, title = "Insights", onClick = onNavigateToInsights)
                                ProfileMenuItem(icon = Icons.Default.Share, title = "Invite Friends", onClick = shareApp)
                                ProfileMenuItem(icon = Icons.AutoMirrored.Filled.HelpOutline, title = "Help & Support", onClick = { /* Support */ })
                                ProfileMenuItem(icon = Icons.Default.Settings, title = "Settings", onClick = onNavigateToSettings)
                            }
                        }
                        
                        item { Spacer(modifier = Modifier.height(100.dp)) }
                    }
                }
            }
        }
    }
}

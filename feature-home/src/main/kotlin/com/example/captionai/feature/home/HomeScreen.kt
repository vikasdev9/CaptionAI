package com.example.captionai.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.captionai.core_ui.theme.*
import com.example.captionai.feature.home.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToCaption: () -> Unit,
    onNavigateToHashtag: () -> Unit,
    onNavigateToBio: () -> Unit,
    onNavigateToReels: () -> Unit,
    onNavigateToPlanner: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = viewModel::refresh,
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundBlack),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
        ) {
            item {
                HomeHeader(
                    userName = uiState.userName,
                    onNotificationClick = { },
                    onProfileClick = { }
                )
            }

            item {
                HomeSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                AISuggestionCard(
                    suggestion = uiState.aiSuggestion,
                    onClick = { /* Suggestion action */ }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "AI Tools",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    TextButton(onClick = { }) {
                        Text("See all", color = TextGray)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                val tools = listOf(
                    ToolItem("Caption", "AI captions that convert", Icons.Default.AutoAwesome, PrimaryGradient, onNavigateToCaption),
                    ToolItem("Hashtags", "Trending tag groups", Icons.Default.Tag, Brush.horizontalGradient(listOf(Color(0xFF00C2FF), Color(0xFF007AFF))), onNavigateToHashtag),
                    ToolItem("Bio Writer", "Magnetic profile bios", Icons.Default.Person, Brush.horizontalGradient(listOf(Color(0xFFFF4D8D), Color(0xFFFF9A8B))), onNavigateToBio),
                    ToolItem("Reel Ideas", "Viral hooks & scripts", Icons.Default.VideoLibrary, Brush.horizontalGradient(listOf(Color(0xFF7B61FF), Color(0xFF00C2FF))), onNavigateToReels),
                    ToolItem("Planner", "Schedule with ease", Icons.Default.CalendarMonth, Brush.horizontalGradient(listOf(Color(0xFF9D4EDD), Color(0xFF7B61FF))), onNavigateToPlanner),
                    ToolItem("Engagement", "Grow your reach", Icons.Default.Insights, Brush.horizontalGradient(listOf(Color(0xFFF9D423), Color(0xFFFF4E50))), {})
                )

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    for (i in tools.indices step 2) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Box(modifier = Modifier.weight(1f)) {
                                val tool = tools[i]
                                ToolFeatureCard(tool.title, tool.subtitle, tool.icon, tool.gradient, tool.onClick)
                            }
                            if (i + 1 < tools.size) {
                                Box(modifier = Modifier.weight(1f)) {
                                    val tool = tools[i + 1]
                                    ToolFeatureCard(tool.title, tool.subtitle, tool.icon, tool.gradient, tool.onClick)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                TrendingNowSection(tags = uiState.trendingTags)
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                RecentGenerationsSection(items = uiState.recentGenerations)
                Spacer(modifier = Modifier.height(100.dp)) // Padding for floating nav
            }
        }
    }
}

data class ToolItem(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val gradient: Brush,
    val onClick: () -> Unit
)

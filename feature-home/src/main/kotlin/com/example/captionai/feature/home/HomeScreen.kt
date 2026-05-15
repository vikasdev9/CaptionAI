package com.example.captionai.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.captionai.core_ui.components.AiFeatureCard
import com.example.captionai.core_ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCaption: () -> Unit,
    onNavigateToHashtag: () -> Unit,
    onNavigateToBio: () -> Unit,
    onNavigateToReels: () -> Unit,
    onNavigateToPlanner: () -> Unit
) {
    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Caption AI",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            brush = AI_Gradient
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { /* Profile */ }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
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
            
            // Greeting
            Text(
                text = "Hello, Creator!",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            )
            Text(
                text = "What magic are we creating today?",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TextGray
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Feature Grid
            val menuItems = listOf(
                HomeMenuItem("Caption Generator", Icons.Default.AutoAwesome, PrimaryGradient, onNavigateToCaption),
                HomeMenuItem("Hashtags", Icons.Default.Tag, Brush.horizontalGradient(listOf(Color(0xFF00C2FF), Color(0xFF007AFF))), onNavigateToHashtag),
                HomeMenuItem("Bio Generator", Icons.Default.Person, Brush.horizontalGradient(listOf(Color(0xFFFF4D8D), Color(0xFFFF9A8B))), onNavigateToBio),
                HomeMenuItem("Reel Ideas", Icons.Default.VideoLibrary, Brush.horizontalGradient(listOf(Color(0xFF7B61FF), Color(0xFF00C2FF))), onNavigateToReels),
                HomeMenuItem("Content Planner", Icons.Default.CalendarMonth, Brush.horizontalGradient(listOf(Color(0xFF9D4EDD), Color(0xFF7B61FF))), onNavigateToPlanner),
                HomeMenuItem("Engagement Tips", Icons.Default.Insights, Brush.horizontalGradient(listOf(Color(0xFFF9D423), Color(0xFFFF4E50))), {})
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(480.dp), // Fixed height for grid within scrollable column
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = false
            ) {
                items(menuItems) { item ->
                    AiFeatureCard(
                        title = item.title,
                        icon = item.icon,
                        gradient = item.gradient,
                        onClick = item.onClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Recent Section
            Text(
                text = "Recent Generations",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            RecentCard("Instagram Fashion Caption", "2 hours ago")
            RecentCard("Photography Hashtag Set", "5 hours ago")
            
            Spacer(modifier = Modifier.height(100.dp)) // Bottom nav space
        }
    }
}

@Composable
fun RecentCard(title: String, time: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20.dp)),
        color = BackgroundCard
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PrimaryPurple.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.History, contentDescription = null, tint = PrimaryPurple)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = time, color = TextGray, fontSize = 12.sp)
            }
        }
    }
}

data class HomeMenuItem(
    val title: String,
    val icon: ImageVector,
    val gradient: Brush,
    val onClick: () -> Unit
)

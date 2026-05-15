package com.example.captionai.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.captionai.core_ui.components.PremiumButton
import com.example.captionai.core_ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Unlock Premium",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Get unlimited AI generations and exclusive features",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            val features = listOf(
                "Unlimited AI Captions",
                "Trending Hashtag Suggestions",
                "Advanced Reel Idea Generator",
                "Priority Support",
                "Ad-free Experience"
            )

            items(features) { feature ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = feature, color = Color.White)
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                SubscriptionPlanCard(
                    title = "Monthly",
                    price = "$9.99/mo",
                    isSelected = false,
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(12.dp))
                SubscriptionPlanCard(
                    title = "Yearly",
                    price = "$79.99/yr",
                    subtitle = "Save 33%",
                    isSelected = true,
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(32.dp))
                PremiumButton(onClick = { /* Handle purchase */ })
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { /* Restore purchases */ }) {
                    Text("Restore Purchases", color = TextGray)
                }
            }
        }
    }
}

@Composable
fun SubscriptionPlanCard(
    title: String,
    price: String,
    subtitle: String? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = if (isSelected) PrimaryPurple else Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        color = if (isSelected) PrimaryPurple.copy(alpha = 0.1f) else BackgroundDark
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                if (subtitle != null) {
                    Text(text = subtitle, color = PrimaryPurple, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
            Text(text = price, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

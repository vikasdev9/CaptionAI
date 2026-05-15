package com.example.captionai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.captionai.core_ui.components.GradientButton
import com.example.captionai.core_ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    val onboardingData = listOf(
        OnboardingData(
            "AI Powered Captions",
            "Generate engaging captions for Instagram, Facebook, and more in seconds.",
            PrimaryPurple
        ),
        OnboardingData(
            "Viral Reel Ideas",
            "Get trending hooks and content ideas tailored to your niche.",
            PrimaryCyan
        ),
        OnboardingData(
            "Smart Planner",
            "Schedule your content and stay ahead of the game with our AI planner.",
            PrimaryPink
        )
    )

    Scaffold(
        containerColor = BackgroundBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPage(onboardingData[page])
            }

            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { iteration ->
                    val color = if (pagerState.currentPage == iteration) PrimaryPurple else Color.DarkGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
                GradientButton(
                    text = if (pagerState.currentPage == 2) "Get Started" else "Next",
                    onClick = {
                        if (pagerState.currentPage < 2) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            onFinish()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(data: OnboardingData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(data.color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for illustration
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(data.color)
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = data.title,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextGray,
                lineHeight = 24.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

data class OnboardingData(val title: String, val description: String, val color: Color)

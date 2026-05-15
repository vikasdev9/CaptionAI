package com.example.captionai.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.captionai.feature.bio.BioScreen
import com.example.captionai.feature.caption.CaptionScreen
import com.example.captionai.feature.hashtag.HashtagScreen
import com.example.captionai.feature.home.HomeScreen
import com.example.captionai.feature.planner.PlannerScreen
import com.example.captionai.feature.reels.ReelsScreen
import com.example.captionai.ui.screens.*
import com.example.captionai.core_ui.theme.*

sealed class Screen(val route: String, val icon: ImageVector? = null) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home", Icons.Default.Home)
    object Caption : Screen("caption")
    object Hashtag : Screen("hashtag")
    object Bio : Screen("bio")
    object Reels : Screen("reels")
    object Planner : Screen("planner", Icons.Default.CalendarToday)
    object Saved : Screen("saved", Icons.Default.BookmarkBorder)
    object Profile : Screen("profile", Icons.Default.PersonOutline)
    object Settings : Screen("settings")
    object Premium : Screen("premium")
    object Privacy : Screen("privacy")
    object Insights : Screen("insights")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ... previous routes ...
        composable(Screen.Splash.route) {
            SplashScreen(onNext = { navController.navigate(Screen.Onboarding.route) })
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinish = { navController.navigate(Screen.Login.route) })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { /* Register */ }
            )
        }
        
        composable(Screen.Home.route) {
            MainContainer(navController) {
                HomeScreen(
                    onNavigateToCaption = { navController.navigate(Screen.Caption.route) },
                    onNavigateToHashtag = { navController.navigate(Screen.Hashtag.route) },
                    onNavigateToBio = { navController.navigate(Screen.Bio.route) },
                    onNavigateToReels = { navController.navigate(Screen.Reels.route) },
                    onNavigateToPlanner = { navController.navigate(Screen.Planner.route) }
                )
            }
        }
        
        composable(Screen.Caption.route) {
            CaptionScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Hashtag.route) {
            HashtagScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Bio.route) {
            BioScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Reels.route) {
            ReelsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Planner.route) {
            MainContainer(navController) {
                PlannerScreen(onBack = { navController.popBackStack() })
            }
        }
        composable(Screen.Saved.route) {
            MainContainer(navController) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Saved Content", color = Color.White)
                }
            }
        }
        composable(Screen.Profile.route) {
            MainContainer(navController) {
                ProfileScreen(
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    onNavigateToInsights = { navController.navigate(Screen.Insights.route) }
                )
            }
        }
        composable(Screen.Settings.route) {
            com.example.captionai.ui.screens.settings.SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPremium = { navController.navigate(Screen.Premium.route) },
                onNavigateToPrivacy = { navController.navigate(Screen.Privacy.route) },
                onNavigateToAuth = { 
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Premium.route) {
            com.example.captionai.ui.screens.settings.PremiumScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Privacy.route) {
            com.example.captionai.ui.screens.settings.PrivacyScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Insights.route) {
            com.example.captionai.ui.screens.InsightsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun MainContainer(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val items = listOf(Screen.Home, Screen.Planner, Screen.Saved, Screen.Profile)
    
    Scaffold(
        containerColor = BackgroundBlack,
        bottomBar = {
            BottomNavigationBar(navController, items)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            content()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<Screen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(36.dp)),
        color = Color.Black.copy(alpha = 0.8f),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(8.dp)
                ) {
                    Icon(
                        screen.icon ?: Icons.Default.Home,
                        contentDescription = null,
                        tint = if (selected) PrimaryPurple else TextGray,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = screen.route.replaceFirstChar { it.uppercase() },
                        color = if (selected) PrimaryPurple else TextGray,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

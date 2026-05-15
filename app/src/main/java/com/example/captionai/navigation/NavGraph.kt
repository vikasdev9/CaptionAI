package com.example.captionai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.captionai.feature.bio.BioScreen
import com.example.captionai.feature.caption.CaptionScreen
import com.example.captionai.feature.hashtag.HashtagScreen
import com.example.captionai.feature.home.HomeScreen
import com.example.captionai.feature.planner.PlannerScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Caption : Screen("caption")
    object Hashtag : Screen("hashtag")
    object Bio : Screen("bio")
    object Reels : Screen("reels")
    object Planner : Screen("planner")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCaption = { navController.navigate(Screen.Caption.route) },
                onNavigateToHashtag = { navController.navigate(Screen.Hashtag.route) },
                onNavigateToBio = { navController.navigate(Screen.Bio.route) },
                onNavigateToReels = { /* navController.navigate(Screen.Reels.route) */ },
                onNavigateToPlanner = { navController.navigate(Screen.Planner.route) }
            )
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
        composable(Screen.Planner.route) {
            PlannerScreen(onBack = { navController.popBackStack() })
        }
    }
}

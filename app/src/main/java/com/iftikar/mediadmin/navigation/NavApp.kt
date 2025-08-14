package com.iftikar.mediadmin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iftikar.mediadmin.presentation.screens.home_screen.HomeScreen
import com.iftikar.mediadmin.presentation.screens.users_screen.UsersScreen

@Composable
fun NavApp() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Routes.HomeScreen) {

        composable<Routes.HomeScreen> {
            HomeScreen(navHostController = navHostController)
        }

        composable<Routes.UsersScreen> {
            UsersScreen(navHostController = navHostController)
        }
    }
}
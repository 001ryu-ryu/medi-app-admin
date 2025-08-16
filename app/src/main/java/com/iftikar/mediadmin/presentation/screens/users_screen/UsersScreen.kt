package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun UsersScreen(
) {
    val usersRootNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                usersNavHostController = usersRootNavController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = usersRootNavController,
            modifier = Modifier.padding(innerPadding),
            startDestination = BottomBarRoutes.Users.route
        ) {
            composable(BottomBarRoutes.Users.route) {
                UserScreenNavHost()
            }

            composable(BottomBarRoutes.Requests.route) {
                RequestsNavHost()
            }

            composable(BottomBarRoutes.BlockList.route) {
                BlockListNavHost()
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    usersNavHostController: NavHostController
) {
    val navBackStackEntry = usersNavHostController.currentBackStackEntryAsState()

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            val isSelected = item.route == navBackStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                onClick = {
                    usersNavHostController.navigate(item.route) {
                        popUpTo(usersNavHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun UserScreenNavHost(
) {
    val userScreenChildNavHostController = rememberNavController()
    NavHost(navController = userScreenChildNavHostController, startDestination = BottomBarScreensRoute.AllUsersScreen) {
        composable<BottomBarScreensRoute.AllUsersScreen> {

            AllUsersScreen(navHostController = userScreenChildNavHostController)
        }

        composable<BottomBarScreensRoute.SpecificUserScreen> {
            val userId = it.toRoute<BottomBarScreensRoute.SpecificUserScreen>().userId
            val viewModel = hiltViewModel<SpecificUserViewModel>(
                key = "user- $userId"
            )
            SpecificUserScreen(
                specificUserViewModel = viewModel,
            )
        }
    }
}

@Composable
private fun RequestsNavHost() {
    val requestNavController = rememberNavController()
    NavHost(navController = requestNavController, startDestination = BottomBarScreensRoute.RequestScreen) {
        composable<BottomBarScreensRoute.RequestScreen> {
            RequestsScreen(
                navHostController = requestNavController
            )
        }

        composable<BottomBarScreensRoute.SpecificUserScreen> {
            val userId = it.toRoute<BottomBarScreensRoute.SpecificUserScreen>().userId
            val viewModel = hiltViewModel<SpecificUserViewModel>(
                key = "user- $userId"
            )
            SpecificUserScreen(
                specificUserViewModel = viewModel
            )
        }
    }
}


@Composable
private fun BlockListNavHost() {
    val blocklistNavController = rememberNavController()
    NavHost(navController = blocklistNavController, startDestination = BottomBarScreensRoute.TestBlockScreen1) {
        composable<BottomBarScreensRoute.TestBlockScreen1> {
            Text(
                text = "Block test screen 1",
                modifier = Modifier.systemBarsPadding()
            )
        }
    }
}


private val bottomNavigationItems = listOf(
    UsersScreenBottomNavigationItem(
        route = BottomBarRoutes.Users.route,
        title = "Users",
        selectedIcon = Icons.Filled.Groups,
        unselectedIcon = Icons.Outlined.Groups
    ),

    UsersScreenBottomNavigationItem(
        route = BottomBarRoutes.Requests.route,
        title = "Requests",
        selectedIcon = Icons.Filled.VerifiedUser,
        unselectedIcon = Icons.Outlined.VerifiedUser
    ),

    UsersScreenBottomNavigationItem(
        route = BottomBarRoutes.BlockList.route,
        title = "Blocklist",
        selectedIcon = Icons.Filled.Block,
        unselectedIcon = Icons.Outlined.Block
    )
)



















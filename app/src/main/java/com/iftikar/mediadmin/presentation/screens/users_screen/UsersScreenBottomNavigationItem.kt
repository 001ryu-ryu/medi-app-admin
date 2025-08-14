package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class UsersScreenBottomNavigationItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Serializable
sealed class BottomBarRoutes(val route: String) {
    @Serializable
    data object Users : BottomBarRoutes("users")
    @Serializable
    data object Requests : BottomBarRoutes("requests")
    @Serializable
    data object BlockList : BottomBarRoutes("block_list")
}

sealed class BottomBarScreensRoute {
    @Serializable
    object AllUsersScreen : BottomBarScreensRoute()
    @Serializable
    object SpecificUserScreen : BottomBarScreensRoute()
    @Serializable
    object TestUserScreen2 : BottomBarScreensRoute()
    @Serializable
    object TestRequestScreen1 : BottomBarScreensRoute()
    @Serializable
    object TestRequestScreen2 : BottomBarScreensRoute()
    @Serializable
    object TestBlockScreen1 : BottomBarScreensRoute()
    @Serializable
    object TestBlockScreen2 : BottomBarScreensRoute()


}








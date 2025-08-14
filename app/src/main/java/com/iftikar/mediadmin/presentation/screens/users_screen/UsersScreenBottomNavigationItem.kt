package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class UsersScreenBottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

sealed class BottomBarRoutes {
    @Serializable
    data object Users
    @Serializable
    data object Requests
    @Serializable
    data object BlockList
}

val items = listOf(
    UsersScreenBottomNavigationItem(
        title = BottomBarRoutes.Users.toString(),
        selectedIcon = Icons.Filled.Groups,
        unselectedIcon = Icons.Outlined.Groups
    ),

    UsersScreenBottomNavigationItem(
        title = BottomBarRoutes.Requests.toString(),
        selectedIcon = Icons.Filled.VerifiedUser,
        unselectedIcon = Icons.Outlined.VerifiedUser
    ),

    UsersScreenBottomNavigationItem(
        title = BottomBarRoutes.BlockList.toString(),
        selectedIcon = Icons.Filled.Block,
        unselectedIcon = Icons.Outlined.Block
    )
)










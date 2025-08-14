package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.mediadmin.domain.model.User
import com.iftikar.mediadmin.presentation.components.user.UserItemComponent

@Composable
fun RequestsScreen(
    usersScreenViewModel: UsersScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = usersScreenViewModel.state.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        when (val userState = state.value) {
            is UsersScreenState.Failure -> {}
            UsersScreenState.Idle -> {}
            UsersScreenState.Loading -> {}
            is UsersScreenState.Success<*> -> {
                val users = userState.data as? List<User> ?: emptyList()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(users.filter { it.isApproved == 0 }) { user ->
                        UserItemComponent(
                            user = user,
                            onClick = {
                                navHostController.navigate(BottomBarScreensRoute.SpecificUserScreen(userId = user.userId))
                            }
                        )
                    }
                }
            }
        }
    }
}




















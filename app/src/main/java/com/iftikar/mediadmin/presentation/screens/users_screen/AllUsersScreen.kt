package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.mediadmin.presentation.components.user.UserItemComponent

@Composable
fun AllUsersScreen(
    allUsersViewModel: AllUsersViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = allUsersViewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when {
            state.value.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.error != null -> {
                state.value.error?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(it)
                    }
                }
            }

            state.value.users.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Empty List")
                }
            }

            state.value.users.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    val approvedUsers = state.value.users.filter { it.isApproved == 1 && it.block == 0 }
                    items(
                        items = approvedUsers,
                        key = {
                            it.userId
                        }
                    ) {user ->
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
































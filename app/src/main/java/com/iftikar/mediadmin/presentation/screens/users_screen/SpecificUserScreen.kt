package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.iftikar.mediadmin.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecificUserScreen(
    specificUserViewModel: SpecificUserViewModel
) {
    val state = specificUserViewModel.state.collectAsState()
    var userState by remember { mutableStateOf<User?>(null) }
    val approveUserState = specificUserViewModel.approveUserState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            userState?.let { user ->
                TopAppBar(
                    title = {
                        Text(
                            text = user.name
                        )
                    },
                    actions = {
                        if (user.isApproved == 0) {
                            TextButton(
                                onClick = {
                                    specificUserViewModel.approveUser(1)
                                }
                            ) {
                                Text(
                                    text = "Approve",
                                    color = Color.Green
                                )
                            }
                        }
                    }
                    )
            }
        }
    ) { innerPadding ->

        when (val specificUser = state.value) {
            is UsersScreenState.Failure -> {}
            UsersScreenState.Idle -> {}
            UsersScreenState.Loading -> {}
            is UsersScreenState.Success<*> -> {
                val user = specificUser.data as? User
                user?.let { user ->
                    userState = user
                    Text(
                        text = user.toString(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
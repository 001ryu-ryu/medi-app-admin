package com.iftikar.mediadmin.presentation.screens.users_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.mediadmin.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecificUserScreen(
    specificUserViewModel: SpecificUserViewModel
) {
    val context = LocalContext.current
    val state = specificUserViewModel.state.collectAsStateWithLifecycle()
    var userState by remember { mutableStateOf<User?>(null) }
    val approveUserFlow = specificUserViewModel.approveUserState
    LaunchedEffect(key1 = approveUserFlow) {
        approveUserFlow.collect { event ->
            when(event) {
                is ApproveUserEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

            }
        }


    }
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

            state.value.user != null -> {
                userState = state.value.user
                Text(
                    text = state.value.user.toString()
                )
            }

            state.value.error != null -> {
                Text(
                    text = state.value.error ?: "Unknown"
                )
            }
        }
    }
}

















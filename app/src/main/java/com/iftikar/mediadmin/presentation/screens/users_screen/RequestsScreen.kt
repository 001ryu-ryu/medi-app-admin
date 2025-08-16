package com.iftikar.mediadmin.presentation.screens.users_screen

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iftikar.mediadmin.presentation.components.user.UserItemComponent
import com.iftikar.mediadmin.shared.SharedUiEventViewModel

@Composable
fun RequestsScreen(
    sharedUiEventViewModel: SharedUiEventViewModel,
    allUsersViewModel: AllUsersViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = allUsersViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.d("Approve-Event-Launch", "Launched Block entered-Request")
        sharedUiEventViewModel.userApprovedFlow.collect {
            Log.d("Approve-Event-Request", it.toString())
            allUsersViewModel.getAllUsers()
        }
    }


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
                    val requests = state.value.users.filter { it.isApproved == 0 && it.block == 0 }
                    items(
                        items = requests,
                        key = {
                            it.userId
                        }
                    ) { user ->
                        UserItemComponent(
                            user = user,
                            onClick = {
                                navHostController.navigate(
                                    BottomBarScreensRoute.SpecificUserScreen(
                                        userId = user.userId
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}



















